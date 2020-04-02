package net.multi.terminal.bff.core.codec;

import net.multi.terminal.bff.exception.SystemException;
import org.springframework.stereotype.Component;

@Component("EmptyApiCodec")
public class EmptyApiCodec implements ApiCodec{

    @Override
    public CommonMsg decode(String encodeMsg) throws SystemException {
        CommonMsg commonMsg = new CommonMsg();
        commonMsg.setMessage(encodeMsg);
        return commonMsg;
    }

    @Override
    public String encode(CommonMsg commonMsg) throws SystemException {
        return commonMsg.getMessage();
    }
}
