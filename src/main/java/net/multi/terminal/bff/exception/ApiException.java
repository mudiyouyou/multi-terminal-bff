package net.multi.terminal.bff.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Data;
import net.multi.terminal.bff.constant.MsgCode;

@Data
public class ApiException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorDesc;
    private HttpResponseStatus httpResponseStatus = HttpResponseStatus.INTERNAL_SERVER_ERROR;

    public ApiException(String message) {
        super(message);
    }

    public ApiException(MsgCode code) {
        super(code.getMessage());
        this.errorCode = code.getCode();
        this.errorDesc = code.getMessage();
    }

    public ApiException(MsgCode code, String errorDesc) {
        super(errorDesc);
        this.errorCode = code.getCode();
        this.errorDesc = errorDesc;
    }

    public ApiException(MsgCode code, HttpResponseStatus status) {
        this(code);
        this.httpResponseStatus = status;
    }


    public ApiException(Throwable cause, MsgCode code, HttpResponseStatus status) {
        super(code.getMessage(), cause);
        this.errorCode = code.getCode();
        this.errorDesc = code.getMessage();
        this.httpResponseStatus = status;
    }

    public ApiException(Throwable cause, MsgCode code) {
        this(cause,code,HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public String toString() {
        return "SystemException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                '}';
    }
}
