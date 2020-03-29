package net.multi.terminal.bff.core.serializer;

import net.multi.terminal.bff.core.codec.CommonMsg;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;

/**
 * Api请求响应转换器
 * 封装了转换的通用逻辑，通过template模式调用具体的转换器实现类
 */
public interface MsgSerializer {
    ApiReq deserialize(String clientId, CommonMsg msg) throws Exception;

    String serialize(ApiRsp rsp) throws Exception;

    String serialize(Throwable throwable) throws Exception;
}
