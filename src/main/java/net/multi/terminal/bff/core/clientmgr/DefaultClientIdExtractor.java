package net.multi.terminal.bff.core.clientmgr;

import com.google.common.base.Splitter;
import io.netty.handler.codec.http.FullHttpRequest;
import net.multi.terminal.bff.exception.SystemException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 默认客户端ID提取器
 * 取URL中最后一段为客户端ID
 */
@Component
public class DefaultClientIdExtractor implements ClientIdExtractor {

    public static final String CLIENT_ID = "clientId";

    @Override
    public String extract(FullHttpRequest httpRequest) throws SystemException {
        List<String> segments = Splitter.on("/").splitToList(httpRequest.uri());
        int size = segments.size();
        if (size < 2 || !CLIENT_ID.equals(segments.get(size - 2)) || StringUtils.isEmpty(segments.get(size - 1)) || !StringUtils.isNumeric(segments.get(size - 1))) {
            throw new SystemException("URL路径错误");
        }
        return segments.get(size - 1);
    }
}
