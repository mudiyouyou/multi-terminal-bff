package net.multi.terminal.bff.core.serializer;

import net.multi.terminal.bff.core.identity.ApiIdentity;
import net.multi.terminal.bff.core.codec.CommonMsg;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;

/**
 * Api请求响应转换器
 * 封装了转换的通用逻辑，通过template模式调用具体的转换器实现类
 */
public interface MsgSerializer {
    ApiReq deserialize(ApiIdentity identity, CommonMsg msg) throws SystemException;

    String serialize(ApiRsp rsp) throws SystemException;

    String serialize(Throwable throwable) throws SystemException;
}
