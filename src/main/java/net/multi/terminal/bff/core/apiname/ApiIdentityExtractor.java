package net.multi.terminal.bff.core.apiname;

import io.netty.handler.codec.http.FullHttpRequest;
import net.multi.terminal.bff.exception.ApiException;

public interface ApiIdentityExtractor {
    public ApiIdentity extract(FullHttpRequest httpRequest) throws ApiException;
}
