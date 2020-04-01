package net.multi.terminal.bff.core.session.app;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.session.SessionInjector;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("AppSessionInjector")
public class AppSessionInjector implements SessionInjector {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void inject(ApiReq req, FullHttpRequest request, FullHttpResponse response) throws ApiException {
        if (StringUtils.isEmpty(req.getTerminalPhysicalNo())) {
            throw new ApiException(MsgCode.E_11010, HttpResponseStatus.BAD_REQUEST);
        }
        req.setSession(new ApiSession(req.getTerminalPhysicalNo(), redisTemplate));
    }
}
