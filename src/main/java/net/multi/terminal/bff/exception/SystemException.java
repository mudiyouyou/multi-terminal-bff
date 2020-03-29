package net.multi.terminal.bff.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Data;
import net.multi.terminal.bff.constant.MsgCode;

@Data
public class SystemException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorDesc;
    private HttpResponseStatus httpResponseStatus = HttpResponseStatus.INTERNAL_SERVER_ERROR;

    public SystemException(String message) {
        super(message);
    }

    public SystemException(MsgCode code) {
        super(code.getMessage());
        this.errorCode = code.getCode();
        this.errorDesc = code.getMessage();
    }

    public SystemException(MsgCode code, HttpResponseStatus status) {
        this(code);
        this.httpResponseStatus = status;
    }


    public SystemException(Throwable cause, MsgCode code, HttpResponseStatus status) {
        super(code.getMessage(), cause);
        this.errorCode = code.getCode();
        this.errorDesc = code.getMessage();
        this.httpResponseStatus = status;
    }

    @Override
    public String toString() {
        return "SystemException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                '}';
    }
}
