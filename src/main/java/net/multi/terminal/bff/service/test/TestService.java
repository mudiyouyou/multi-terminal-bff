package net.multi.terminal.bff.service.test;

import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.annotation.Api;
import net.multi.terminal.bff.core.annotation.ApiMapping;
import net.multi.terminal.bff.core.annotation.IgnoreAuth;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.stereotype.Service;

@Api
@Service
public class TestService {
    @IgnoreAuth
    @ApiMapping(name = "Test")
    public ApiRsp test(ApiReq req) {
        return MsgCode.E_00000.getRsp();
    }
}
