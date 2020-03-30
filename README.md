# 多终端前置系统

[项目源代码地址](https://github.com/mudiyouyou/multi-terminal-bff)

在工作中我们经常遇到为app，h5, outside api 开发不同的前置系统，往往同一个业务逻辑需要开发三种api的形式，为什么要这样呢？
它们只是报文格式，加解密需求，会话形式不同等等而已。

于是开发了这个多终端前置系统，采用微内核模式封装上述场景中通用流程，将其中一部分用接口开放出来。

其中使用的开源框架如下：
使用springboot做集成，使用netty做web server，使用hystrix做熔断器，使用redis做分布式session，使用jmx做动态配置。
整个框架只做了最少的事情，其他交给它未来的使用者们。

顺便说一下：
使用者分为两种角色， 框架的二次开发者和具体业务逻辑开发者。

二次开发者需要关注通用流程中的开放接口，它们在：net.multi.terminal.bff.core

具体业务逻辑开发者需要关心以下路径，它们是：net.multi.terminal.bff.service、net.multi.terminal.bff.model

其中net.multi.terminal.bff.model是业务通用模型，它们是：请求，响应，会话 和 客户端上下文

前三个浅而易见，而客户端上下文是什么？我们将不同的终端定义成不同的客户端，并给它一个固定的ID，它所使用的个性化实现类被写在multi-terminal-bff/src/main/resources/client-setting.json

    [
      {
        "clientId": "1",
        "serializerName":"AppMsgSerializer",
        "apiCodecName":"EmptyApiCodec",
        "sessionInjectorName":"AppSessionInjector",
        "rspContentType":"text/plain;charset=UTF-8"
      },
      {
        "clientId": "2",
        "serializerName":"FormJsonMsgSerializer",
        "apiCodecName":"EmptyApiCodec",
        "sessionInjectorName":"WebSessionInjector",
        "rspContentType":"application/json;charset=UTF-8"
      },
      {
        "clientId": "3",
        "serializerName":"DoubleJsonMsgSerializer",
        "apiCodecName":"EmptyApiCodec",
        "sessionInjectorName":"WebSessionInjector",
        "rspContentType":"application/json;charset=UTF-8"
      }
    ]

内容如下，非常通俗易懂，只要你认真看了这个类net.multi.terminal.bff.web.HttpApiHanler
没错，它描述了整个请求、处理、响应的过程。

### 如何使用？

1. 部署环境
    下载源码
    `git clone https://github.com/mudiyouyou/multi-terminal-bff.git`
    docker 安装 redis
    `docker run -d -p 6379:6379 -v /Users/apple/apps/docker/redis:/data --name redis redis`
2. 安装依赖
    `cd multi-terminal-bff`
    `mvn compile -Dmaven.test.skip=true`
3. 修改配置
    在application-dev.properties中有redis配置，如果你的环境使用不同的IP和PORT
4. 运行程序
    `mvn exec:java -Dexec.mainClass=net.multi.terminal.bff.MutliTerminalBff`
5. 测试接口
    `curl -X POST http://localhost:8082/api/clientId/2 -d 'application=Count'`
    返回
    `{"count":1,"respCode":"00000","respDesc":"成功"}`
    
    注：默认端口为8082  默认内部提供一个用于测试的计数器业务接口，使用会话(redis)存储当前计数器值。配置文件client-setting.json中设置clientId:2 采用Form提交返回Json，空加解密实现，使用cookie保存sessionid的形式

好了就写到这吧，更多细节在代码里面，如果你在这个版本上有新的扩展请贡献出来。

# Multi-terminal front system

[Project source code address] (https://github.com/mudiyouyou/multi-terminal-bff)

In our work, we often encounter developing different front-end systems for app, h5, outside api. Often the same business logic needs to develop three forms of api. Why is this the case?
They are just the message format, encryption and decryption requirements, different session forms, and so on.

Therefore, this multi-terminal front-end system was developed, and the micro-kernel mode was used to encapsulate the general process in the above scenario, and a part of it was opened with an interface.

The open source frameworks used are as follows:
Use springboot for integration, netty for web server, hystrix for fuse, redis for distributed session, and jmx for dynamic configuration.
The entire framework does only the fewest things, leaving the rest to its future users.

By the way:
Users are divided into two roles, secondary developers of the framework and developers of specific business logic.

Secondary developers need to pay attention to the open interfaces in the general process, they are at: net.multi.terminal.bff.core

Specific business logic developers need to care about the following paths, which are: net.multi.terminal.bff.service, net.multi.terminal.bff.model

Where net.multi.terminal.bff.model is the general business model, they are: request, response, session and client context

The first three are easy to see, and what is the client context? We define different terminals as different clients and give it a fixed ID. The personalized implementation class used by it is written in multi-terminal-bff / src / main / resources / client-setting.json

    [
      {
        "clientId": "1",
        "serializerName": "AppMsgSerializer",
        "apiCodecName": "EmptyApiCodec",
        "sessionInjectorName": "AppSessionInjector",
        "rspContentType": "text / plain; charset = UTF-8"
      },
      {
        "clientId": "2",
        "serializerName": "FormJsonMsgSerializer",
        "apiCodecName": "EmptyApiCodec",
        "sessionInjectorName": "WebSessionInjector",
        "rspContentType": "application / json; charset = UTF-8"
      },
      {
        "clientId": "3",
        "serializerName": "DoubleJsonMsgSerializer",
        "apiCodecName": "EmptyApiCodec",
        "sessionInjectorName": "WebSessionInjector",
        "rspContentType": "application / json; charset = UTF-8"
      }
    ]

The content is as follows, very easy to understand, as long as you carefully look at this class net.multi.terminal.bff.web.HttpApiHanler
That's right, it describes the entire process of requesting, processing, and responding.

### how to use?

Deployment environment
    Download the source code
    `git clone https: // github.com / mudiyouyou / multi-terminal-bff.git`
    docker install redis
    `docker run -d -p 6379: 6379 -v / Users / apple / apps / docker / redis: / data --name redis redis`
2. Installation dependencies
    `cd multi-terminal-bff`
    `mvn compile -Dmaven.test.skip = true`
3. Modify the configuration
    Redis configuration in application-dev.properties, if your environment uses different IP and PORT
4. Run the program
    `mvn exec: java -Dexec.mainClass = net.multi.terminal.bff.MutliTerminalBff`
5. Test interface
    `curl -X POST http: // localhost: 8082 / api / clientId / 2 -d 'application = Count'`
    return
    `{" count ": 1," respCode ":" 00000 "," respDesc ​​":" Success "}`
    
    Note: The default port is 8082. By default, a counter business interface is provided for testing. The session (redis) is used to store the current counter value. Set the clientId: 2 in the configuration file client-setting.json. Use Form to return Json, empty encryption and decryption, and use cookie to save the sessionid

Okay, write it here. More details are in the code. If you have new extensions in this version, please contribute.