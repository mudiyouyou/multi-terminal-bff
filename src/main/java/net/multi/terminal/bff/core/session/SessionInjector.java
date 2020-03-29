package net.multi.terminal.bff.core.session;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiReq;

public interface SessionInjector {
    void inject(ApiReq req, FullHttpRequest request, FullHttpResponse response) throws SystemException;
}
