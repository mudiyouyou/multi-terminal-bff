package net.multi.terminal.bff.core.codec;

import org.springframework.stereotype.Component;

@Component("EmptyApiCodec")
public class EmptyApiCodec implements ApiCodec{

    @Override
    public CommonMsg decode(String clientId, String encodeMsg) throws Exception {
        CommonMsg commonMsg = new CommonMsg();
        commonMsg.setMessage(encodeMsg);
        return commonMsg;
    }

    @Override
    public String encode(CommonMsg commonMsg) throws Exception {
        return commonMsg.getMessage();
    }
}
