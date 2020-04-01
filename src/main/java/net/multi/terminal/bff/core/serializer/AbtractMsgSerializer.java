package net.multi.terminal.bff.core.serializer;

import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ApiRsp;
import org.apache.commons.lang.StringUtils;

@Slf4j
public abstract class AbtractMsgSerializer implements MsgSerializer{
    protected String getApiName(String name) {
        return StringUtils.substringBefore(name, ".Req");
    }

    @Override
    public String serialize(Throwable throwable) throws Exception {
        return serialize(toRsp(throwable));
    }

    public ApiRsp toRsp(Throwable throwable) {
        if (throwable instanceof ApiException) {
            log.error("[业务错误]", throwable);
            ApiRsp rsp = new ApiRsp();
            ApiException bizError = (ApiException) throwable;
            rsp.setRespCode(bizError.getErrorCode() != null ? bizError.getErrorCode() : MsgCode.E_19999.getCode());
            rsp.setRespDesc(bizError.getErrorDesc() != null ? bizError.getErrorDesc() : MsgCode.E_19999.getMessage());
            return rsp;
        } else if (throwable instanceof ApiException) {
            log.debug("[系统错误]", throwable);
            ApiRsp rsp = new ApiRsp();
            ApiException bizError = (ApiException) throwable;
            rsp.setRespCode(bizError.getErrorCode() != null ? bizError.getErrorCode() : MsgCode.E_19999.getCode());
            rsp.setRespDesc(bizError.getErrorDesc() != null ? bizError.getErrorDesc() : MsgCode.E_19999.getMessage());
            return rsp;
        } else {
            log.error("[未知错误]", throwable);
            ApiRsp rsp = new ApiRsp();
            rsp.setRespCode(MsgCode.E_19999.getCode());
            rsp.setRespDesc(MsgCode.E_19999.getMessage());
            return rsp;
        }
    }
}
