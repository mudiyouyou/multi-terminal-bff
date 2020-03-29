package net.multi.terminal.bff;

import net.multi.terminal.bff.web.ApiNettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动入口类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"net.multi.terminal.bff"})
public class MutliTerminalBff {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(MutliTerminalBff.class, args);
        ApiNettyServer nettyServer = ctx.getBean(ApiNettyServer.class);
        String port = ctx.getEnvironment().getProperty("netty.port");
        nettyServer.start(Integer.parseInt(port));
    }
}

