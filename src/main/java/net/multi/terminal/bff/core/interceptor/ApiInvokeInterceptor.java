package net.multi.terminal.bff.core.interceptor;

import net.multi.terminal.bff.core.apimgr.ApiInvoker;
import net.multi.terminal.bff.core.apimgr.ApiMappingService;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 拦截器最后一个处理用于拉起ApiInvoker,处理具体业务逻辑
 * 生产环境支持断路器功能
 */
@Component
public class ApiInvokeInterceptor implements ApiInterceptor {
    @Autowired
    private ApiMappingService apiMapping;

    @Override
    public int priority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public ApiRsp handle(ApiReq inputMessage, ApiInterceptorChain chain) throws ApiException {
        ApiInvoker apiInvoker = apiMapping.route(inputMessage.getApiName());
        return apiInvoker.doInvoke(inputMessage);
    }
}
