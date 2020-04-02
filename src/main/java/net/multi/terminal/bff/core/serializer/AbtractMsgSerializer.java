package net.multi.terminal.bff.core.serializer;

import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiRsp;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;

@Slf4j
public abstract class AbtractMsgSerializer implements MsgSerializer{
    @Override
    public String serialize(Throwable throwable) throws SystemException {
        return serialize(toRsp(throwable));
    }

    public ApiRsp toRsp(Throwable throwable) {
        if (throwable instanceof SystemException) {
            log.debug("系统错误", throwable);
            ApiRsp rsp = new ApiRsp();
            SystemException bizError = (SystemException) throwable;
            rsp.setRespCode(Optional.ofNullable(bizError.getErrorCode()).orElse(MsgCode.E_19999.getCode()));
            rsp.setRespDesc(Optional.ofNullable(bizError.getMessage()).orElse(MsgCode.E_19999.getMessage()));
            return rsp;
        } else if (throwable instanceof ApiException) {
            log.error("业务错误", throwable);
            ApiRsp rsp = new ApiRsp();
            ApiException bizError = (ApiException) throwable;
            rsp.setRespCode(Optional.ofNullable(bizError.getErrorCode()).orElse(MsgCode.E_19999.getCode()));
            rsp.setRespDesc(Optional.ofNullable(bizError.getMessage()).orElse(MsgCode.E_19999.getMessage()));
            return rsp;
        } else {
            log.error("未知错误", throwable);
            ApiRsp rsp = new ApiRsp();
            rsp.setRespCode(MsgCode.E_19999.getCode());
            rsp.setRespDesc(MsgCode.E_19999.getMessage());
            return rsp;
        }
    }
}
