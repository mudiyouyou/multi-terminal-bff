# multi-terminal-bff
BFF for multiple terminals

In our work, we often encounter the development of different api bff systems for app, h5, outside api, often the same business logic needs to develop three forms of api, why is it so?
They are just the message format, encryption and decryption requirements, different session forms, and so on.

So I developed this BFF system, using micro-kernel mode to encapsulate the general process in the above scenario, and opening up the difference with an interface.

Open source framework uses springboot for integration, netty for web server, hystrix for fuse, redis for distributed session, and jmx for dynamic configuration.

The framework does only the fewest things, leaving the rest to its future users.

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
