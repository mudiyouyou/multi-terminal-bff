package net.multi.terminal.bff.core.session.web;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import net.multi.terminal.bff.core.session.SessionInjector;
import net.multi.terminal.bff.core.util.CookieUtil;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("WebSessionInjector")
public class WebSessionInjector implements SessionInjector {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void inject(ApiReq req, FullHttpRequest request, FullHttpResponse response) {
        if (req.getSession() == null) {
            String sessionId = CookieUtil.getOrCreateSessionId(request);
            req.setSession(new ApiSession(sessionId, redisTemplate));
        }else{
            CookieUtil.injectSessionId(req,request,response);
        }
    }
}
