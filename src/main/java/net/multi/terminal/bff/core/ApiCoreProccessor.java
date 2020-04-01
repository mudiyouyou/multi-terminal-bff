package net.multi.terminal.bff.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.apiname.ApiIdentity;
import net.multi.terminal.bff.core.clientmgr.ClientContextMgr;
import net.multi.terminal.bff.core.codec.ApiCodec;
import net.multi.terminal.bff.core.codec.CommonMsg;
import net.multi.terminal.bff.core.interceptor.ApiInterceptorChainContext;
import net.multi.terminal.bff.core.serializer.MsgSerializer;
import net.multi.terminal.bff.core.session.SessionInjector;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import net.multi.terminal.bff.model.ClientContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

import static net.multi.terminal.bff.core.util.NettyUtil.buildResponse;
import static net.multi.terminal.bff.core.util.NettyUtil.send;

@Slf4j
@Component
public class ApiCoreProccessor {
    @Autowired
    private ApiInterceptorChainContext chainContext;

    @Autowired
    private ClientContextMgr clientContextMgr;

    public Void process(ApiIdentity identity, ChannelHandlerContext ctx, FullHttpRequest httpRequest) throws Exception {
        CommonMsg commonMsgReq = null;
        try {
            ClientContext context = clientContextMgr.getContext(identity.getClientId());
            // 打印请求报文整体内容
            String inputMsg = httpRequest.content().toString(Charset.forName("UTF-8"));
            log.debug("原始请求:{}", inputMsg);
            ApiCodec apiCodec = context.getApiCodec();
            commonMsgReq = apiCodec.decode(identity.getClientId(), inputMsg);
            // 获取报文转换器
            MsgSerializer serializer = context.getSerializer();
            // 反序列化请求
            ApiReq req = serializer.deserialize(identity.getClientId(), commonMsgReq);
            // 注入session对象
            SessionInjector injector = context.getInjector();
            injector.inject(req, httpRequest, null);
            // 设置请求头
            req.setHeaders(httpRequest.headers());
            // 设置api名称
            req.setApplication(identity.getApiName());
            // 调用拦截器链
            ApiRsp rsp = chainContext.handle(req);
            log.debug("原始响应:{}", rsp);
            // 序列化响应
            String outputMsg = serializer.serialize(rsp);
            // 加密响应报文
            CommonMsg commonMsgRsp = new CommonMsg();
            commonMsgRsp.setSecretKey(commonMsgReq.getSecretKey());
            commonMsgRsp.setMessage(outputMsg);
            // 生成Netty响应对象
            DefaultFullHttpResponse httpResponse = buildResponse(HttpResponseStatus.OK, context.getRspContentType());
            // 检查是否注入sessionId
            injector.inject(req, httpRequest, httpResponse);
            // 发送Netty响应对象
            send(ctx, apiCodec.encode(commonMsgRsp), httpResponse);
        } catch (Exception e) {
            log.error("API执行失败", e);
            exceptionHandle(e, commonMsgReq);
        }
        return null;
    }

    public void exceptionHandle(Throwable throwable, CommonMsg msg) throws ApiException {
        // 序列化
        try {
            ClientContext context = clientContextMgr.getContext(msg.getClientId());
            MsgSerializer convertor = context.getSerializer();
            String errorMsg = convertor.serialize(throwable);
            // 加密
            ApiCodec apiCodec = context.getApiCodec();
            CommonMsg commonMsgRsp = new CommonMsg();
            commonMsgRsp.setSecretKey(msg.getSecretKey());
            commonMsgRsp.setMessage(errorMsg);
            String secretMsg = apiCodec.encode(commonMsgRsp);
            throw new ApiException(MsgCode.E_11015, secretMsg);
        } catch (Exception e) {
            if (throwable instanceof ApiException) {
                throw (ApiException) e;
            } else {
                throw new ApiException(MsgCode.E_11015, e.getMessage());
            }
        }
    }
}
