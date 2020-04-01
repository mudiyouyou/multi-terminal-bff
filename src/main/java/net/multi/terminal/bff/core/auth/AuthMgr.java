package net.multi.terminal.bff.core.auth;

import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import net.multi.terminal.bff.model.ApiSession;
import org.springframework.stereotype.Component;

@Component
public class AuthMgr {
    private final static String AUTHORIZE = "authorize";
    public boolean checkAuth(ApiReq inputMessage) {
        if (inputMessage.getSession().get(AUTHORIZE) == null) {
            return false;
        } else {
            return true;
        }
    }

    public ApiRsp response(ApiReq inputMessage) {
        return new ApiRsp(MsgCode.E_11012.getCode(), MsgCode.E_11012.getMessage());
    }

    public void authorize(ApiSession apiSession) {
        apiSession.put(AUTHORIZE,true);
    }
}
