# 针对多终端的前置系统（app，h5, api）

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

好了就写到这吧，更多细节在代码里面，如果你在这个版本上有新的扩展请提交过来吧。

# multi-terminal-bff
BFF for multiple terminals

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

The content is as follows, very easy to understand, as long as you carefully look at this class net.multi.terminal.bff.web.HttpApiHanler
That's right, it describes the entire process of requesting, processing, and responding.

Okay, write it here. More details are in the code. If you have new extensions in this version, please submit them.

