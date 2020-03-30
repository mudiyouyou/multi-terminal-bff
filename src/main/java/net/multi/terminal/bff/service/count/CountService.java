package net.multi.terminal.bff.service.count;

import lombok.extern.slf4j.Slf4j;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.annotation.Api;
import net.multi.terminal.bff.core.annotation.ApiMapping;
import net.multi.terminal.bff.core.annotation.IgnoreAuth;
import net.multi.terminal.bff.exception.BusinessException;
import net.multi.terminal.bff.model.ApiSession;
import net.multi.terminal.bff.service.count.req.CountReq;
import net.multi.terminal.bff.service.count.rsp.CountRsp;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Api
public class CountService {

    @IgnoreAuth
    @ApiMapping(name = "Count")
    public CountRsp count(CountReq req) throws BusinessException {
        ApiSession session = req.getSession();
        session.putIfAbsent("count", 0);
        CountRsp rsp = new CountRsp(MsgCode.E_00000.getCode(), MsgCode.E_00000.getMessage());
        Integer count = (Integer) session.get("count") + 1;
        session.put("count", count);
        rsp.setCount(count);
//        if (System.currentTimeMillis() % 2 == 0) {
//            throw new BusinessException(MsgCode.E_11000);
//        }
        return rsp;
    }

}
