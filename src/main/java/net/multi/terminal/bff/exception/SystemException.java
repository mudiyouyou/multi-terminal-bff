package net.multi.terminal.bff.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import net.multi.terminal.bff.constant.MsgCode;

public class SystemException extends ApiException {
    public SystemException(String message) {
        super(message);
    }

    public SystemException(MsgCode code) {
        super(code);
    }

    public SystemException(MsgCode code, String errorDesc) {
        super(code, errorDesc);
    }

    public SystemException(MsgCode code, HttpResponseStatus status) {
        super(code, status);
    }

    public SystemException(Throwable cause, MsgCode code, HttpResponseStatus status) {
        super(cause, code, status);
    }

    public SystemException(Throwable cause, MsgCode code) {
        super(cause, code);
    }
}
