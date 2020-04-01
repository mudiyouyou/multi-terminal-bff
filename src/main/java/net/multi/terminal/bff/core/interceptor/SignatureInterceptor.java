package net.multi.terminal.bff.core.interceptor;

import com.google.common.base.Splitter;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * 通用验签
 * To Do 启动需要加上@Component
 */
public class SignatureInterceptor implements ApiInterceptor {
    private Set<String> blackList;
    @Value("${api.sign.blacklist}")
    private String signBlackList;

    @PostConstruct()
    public void loadSignBlackList(){
        blackList = new HashSet<>(Splitter.on(",").omitEmptyStrings().splitToList(signBlackList));
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public ApiRsp handle(ApiReq inputMessage, ApiInterceptorChain chain) throws ApiException, ApiException {
        // 是否认证
        if (!isInBlackList(inputMessage)){
            return chain.handle(inputMessage);
        }
        // 获取请求令牌
        String sign = getSign(inputMessage);
        // 检验令牌
        checkSign(sign,inputMessage);
        return chain.handle(inputMessage);
    }

    private void checkSign(String sign, ApiReq inputMessage) {
        // TO DO
    }

    private String getSign(ApiReq inputMessage) {
        return inputMessage.getSignature();
    }

    private boolean isInBlackList(ApiReq inputMessage) {
        return blackList.contains(inputMessage.getApplication());
    }
}
