package net.multi.terminal.bff.web;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class ApiNettyServer {
    @Autowired
    private ApiHandlerInitializer initializer;

    public void start(int port) throws InterruptedException {
        NioEventLoopGroup parentGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG,1024*1024)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.SO_TIMEOUT,6000)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,6000);
            bootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer);
            Channel channel = bootstrap.bind(port).sync().channel();
            log.info("Netty服务器启动，监听端口:{}", port);
            channel.closeFuture().sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
