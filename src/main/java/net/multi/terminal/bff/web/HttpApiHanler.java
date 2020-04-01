package net.multi.terminal.bff.web;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.config.HystrixConfig;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.ApiCoreProccessor;
import net.multi.terminal.bff.core.apimgr.ApiMappingService;
import net.multi.terminal.bff.core.apimgr.ApiRunContextMgr;
import net.multi.terminal.bff.core.apiname.ApiIdentity;
import net.multi.terminal.bff.core.apiname.ApiIdentityExtractor;
import net.multi.terminal.bff.core.ApiHystrixCommand;
import net.multi.terminal.bff.core.clientmgr.ClientContextMgr;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ClientContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static net.multi.terminal.bff.core.util.NettyUtil.buildResponse;
import static net.multi.terminal.bff.core.util.NettyUtil.send;

/**
 * 核心类的主要流程
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class HttpApiHanler extends SimpleChannelInboundHandler<HttpObject> {
    @Autowired
    private ApiIdentityExtractor identityExtractor;

    @Autowired
    private ApiCoreProccessor proccessor;

    @Autowired
    private HystrixConfig hystrixConfig;

    @Autowired
    private ClientContextMgr clientContextMgr;

    @Autowired
    private ApiMappingService apiMapping;

    @Override
    protected void channelRead0(ChannelHandlerContext nettyContext, HttpObject msg) throws Exception {
        // 过滤Http全报文
        if (!(msg instanceof FullHttpRequest)) {
            return;
        }
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        // 过滤POST请求报文
        if (!"POST".equals(httpRequest.method().name())) {
            throw new ApiException(MsgCode.E_11001, HttpResponseStatus.BAD_REQUEST);
        }
        // 获取clientId
        ApiIdentity identity = identityExtractor.extract(httpRequest);
        ClientContext clientContext = clientContextMgr.getContext(identity.getClientId());
        if (clientContext == null) {
            throw new ApiException(MsgCode.E_11000, HttpResponseStatus.BAD_REQUEST);
        }
        if (apiMapping.route(identity.getApiName()) == null) {
            throw new ApiException(MsgCode.E_11003, HttpResponseStatus.BAD_REQUEST);
        }
        // 异步执行
        new ApiHystrixCommand(identity, clientContext, proccessor, nettyContext, httpRequest, hystrixConfig).queue();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        send(ctx, throwable.getMessage() == null ? MsgCode.E_19999.getMessage() : throwable.getMessage(),
                buildResponse(HttpResponseStatus.BAD_REQUEST, "text/plain;charset=UTF-8"));
    }
}
