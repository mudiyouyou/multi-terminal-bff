package net.multi.terminal.bff.core.util;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;

public class NettyUtil {
    public static void send(ChannelHandlerContext ctx, String outputMsg, FullHttpResponse response) {
        response.content().writeBytes(Unpooled.copiedBuffer(outputMsg, Charset.forName("UTF-8")));
        ctx.writeAndFlush(response).addListeners(ChannelFutureListener.CLOSE);
    }

    public static DefaultFullHttpResponse buildResponse(HttpResponseStatus status, String contentType) {
        DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
        return httpResponse;
    }
}
