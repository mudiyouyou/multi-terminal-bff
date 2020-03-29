package net.multi.terminal.bff.web;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.clientmgr.ClientContextMgr;
import net.multi.terminal.bff.core.clientmgr.ClientIdExtractor;
import net.multi.terminal.bff.core.codec.ApiCodec;
import net.multi.terminal.bff.core.codec.CommonMsg;
import net.multi.terminal.bff.core.interceptor.ApiInterceptorChainContext;
import net.multi.terminal.bff.core.serializer.MsgSerializer;
import net.multi.terminal.bff.core.session.SessionInjector;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import net.multi.terminal.bff.model.ClientContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 核心类的主要流程
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class HttpApiHanler extends SimpleChannelInboundHandler<HttpObject> {
    @Autowired
    private ApiInterceptorChainContext chainContext;
    @Autowired
    private ClientIdExtractor clientIdExtractor;
    @Autowired
    private ClientContextMgr clientContextMgr;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        CommonMsg commonMsgReq = null;
        try {
            // 过滤Http全报文
            if (!(msg instanceof FullHttpRequest)) {
                throw new SystemException(MsgCode.E_10001, HttpResponseStatus.BAD_REQUEST);
            }
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            // 过滤POST请求报文
            if (!"POST".equals(httpRequest.method().name())) {
                throw new SystemException(MsgCode.E_11001, HttpResponseStatus.BAD_REQUEST);
            }
            // 获取clientId
            String clientId = clientIdExtractor.extract(httpRequest);
            ClientContext context = clientContextMgr.getContext(clientId);
            // 打印请求报文整体内容
            String inputMsg = httpRequest.content().toString(Charset.forName("UTF-8"));
            log.debug("原始请求:{}", inputMsg);
            ApiCodec apiCodec = context.getApiCodec();
            commonMsgReq = apiCodec.decode(clientId, inputMsg);
            // 获取报文转换器
            MsgSerializer serializer = context.getSerializer();
            // 反序列化请求
            ApiReq req = serializer.deserialize(clientId, commonMsgReq);
            // 注入session对象
            SessionInjector injector = context.getInjector();
            injector.inject(req, httpRequest, null);
            log.debug("原始响应:{}", req);
            // 设置请求头
            req.setHeaders(httpRequest.headers());
            // 调用拦截器链
            ApiRsp rsp = chainContext.handle(req);
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
            log.error("API执行失败",e);
            exceptionHandle(ctx, e, commonMsgReq);
        }
    }

    private DefaultFullHttpResponse buildResponse(HttpResponseStatus status, String contentType) {
        DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
        return httpResponse;
    }

    private void send(ChannelHandlerContext ctx, String outputMsg, FullHttpResponse response) {
        response.content().writeBytes(Unpooled.copiedBuffer(outputMsg, Charset.forName("UTF-8")));
        ctx.writeAndFlush(response).addListeners(ChannelFutureListener.CLOSE);
    }

    public void exceptionHandle(ChannelHandlerContext ctx, Throwable throwable, CommonMsg msg) throws Exception {
        if (msg == null || StringUtils.isEmpty(msg.getClientId())) {
            send(ctx, throwable.getMessage() == null ? MsgCode.E_19999.getMessage() : throwable.getMessage(),
                    buildResponse(HttpResponseStatus.BAD_REQUEST, "text/plain;charset=UTF-8"));
            return;
        }
        // 序列化
        ClientContext context = clientContextMgr.getContext(msg.getClientId());
        MsgSerializer convertor = context.getSerializer();
        String errorMsg = convertor.serialize(throwable);
        // 加密
        ApiCodec apiCodec = context.getApiCodec();
        CommonMsg commonMsgRsp = new CommonMsg();
        commonMsgRsp.setSecretKey(msg.getSecretKey());
        commonMsgRsp.setMessage(errorMsg);
        String secretMsg = apiCodec.encode(commonMsgRsp);
        // 转换为Http响应
        if (throwable instanceof SystemException) {
            send(ctx, secretMsg, buildResponse(((SystemException) throwable).getHttpResponseStatus(), context.getRspContentType()));
        } else {
            send(ctx, secretMsg, buildResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR, context.getRspContentType()));
        }
    }


}
