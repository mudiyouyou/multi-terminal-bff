package net.multi.terminal.bff.core.clientmgr;

import io.netty.handler.codec.http.FullHttpRequest;
import net.multi.terminal.bff.exception.SystemException;

public interface ClientIdExtractor {
    public String extract(FullHttpRequest httpRequest) throws SystemException;
}
