package net.multi.terminal.bff.exception;


import lombok.Data;
import net.multi.terminal.bff.constant.MsgCode;

@Data
public class BusinessException extends Exception {
	private String errorCode;
	private String errorDesc;
	private static final long serialVersionUID = 1L;

    public BusinessException(MsgCode code, String errorDesc) {
        this(code);
        this.errorDesc = errorDesc;
    }

    public BusinessException(MsgCode code){
	    super(code.getCode());
		this.errorCode = code.getCode();
		this.errorDesc = code.getMessage();
	}


	public BusinessException(Throwable cause, MsgCode code){
        super(code.getCode(),cause);
        this.errorCode = code.getCode();
        this.errorDesc = code.getMessage();
	}

    @Override
    public String toString() {
        return "BusinessException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                '}';
    }
}
