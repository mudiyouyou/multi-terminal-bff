package net.multi.terminal.bff.core.util;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import net.multi.terminal.bff.core.constant.CoreKeys;
import net.multi.terminal.bff.model.ApiReq;
import org.apache.commons.lang.StringUtils;

import java.util.Set;
import java.util.UUID;

public class CookieUtil {

    public static String getOrCreateSessionId(FullHttpRequest httpRequest) {
        String cookieValue = httpRequest.headers().get(HttpHeaderNames.COOKIE);
        if (StringUtils.isEmpty(cookieValue)) {
            return createSessionId();
        }
        Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieValue);
        String sessionId = null;
        for (Cookie cookie : cookies) {
            if (CoreKeys.API_SESSIONID.equals(cookie.name())) {
                sessionId = cookie.value();
            }
        }
        if (sessionId == null) {
            return createSessionId();
        }
        return sessionId;
    }

    private static String createSessionId() {
        return UUID.randomUUID().toString();
    }

    public static void injectSessionId(ApiReq req, FullHttpRequest request, FullHttpResponse response) {
        String cookieValue = request.headers().get(HttpHeaderNames.COOKIE);
        if (StringUtils.isEmpty(cookieValue)) {
            doInjectSessionId(req.getSession().getSessionId(), response);
        }
    }

    private static void doInjectSessionId(String sessionId, FullHttpResponse response) {
        Cookie cookie = new DefaultCookie(CoreKeys.API_SESSIONID, sessionId);
        cookie.setMaxAge(36000);
        cookie.setPath("/");
        response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookie));
    }
}
