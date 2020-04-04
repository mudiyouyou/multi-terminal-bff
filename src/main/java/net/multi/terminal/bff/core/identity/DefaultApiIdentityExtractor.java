package net.multi.terminal.bff.core.identity;

import com.google.common.base.Splitter;
import io.netty.handler.codec.http.FullHttpRequest;
import net.multi.terminal.bff.exception.ApiException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 默认客户端ID和API名称提取器
 * http://xxx.com/api/clientId/1/apiName/Count   其中1为客户端ID
 */
@Component
public class DefaultApiIdentityExtractor implements ApiIdentityExtractor {

    public static final String CLIENT_ID = "clientId";
    public static final String API_NAME = "apiName";
    @Override
    public ApiIdentity extract(FullHttpRequest httpRequest) throws ApiException {
        List<String> segments = Splitter.on("/").splitToList(httpRequest.uri());
        String clientId = null;
        String apiName = null;
        try {
            for (int i = 0; i < segments.size(); i++) {
                if (CLIENT_ID.equals(segments.get(i))){
                    clientId = segments.get(i+1);
                    continue;
                }
                if (API_NAME.equals(segments.get(i))){
                    apiName = segments.get(i+1);
                    continue;
                }
            }
            if (clientId==null || apiName == null) {
                throw new IllegalArgumentException();
            }
            return new ApiIdentity(clientId,apiName);
        } catch (Exception e) {
            throw new ApiException("URL路径错误");
        }
    }
}
