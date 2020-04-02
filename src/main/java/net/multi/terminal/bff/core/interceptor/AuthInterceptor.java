package net.multi.terminal.bff.core.interceptor;

import net.multi.terminal.bff.core.apimgr.ApiRunContext;
import net.multi.terminal.bff.core.apimgr.ApiRunContextMgr;
import net.multi.terminal.bff.core.auth.AuthMgr;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通用认证
 * 添加上@IgnoreAuth的接口不需要鉴权
 */
@Component
public class AuthInterceptor implements ApiInterceptor {
    @Autowired
    private ApiRunContextMgr apiRunContextMgr;
    @Autowired
    private AuthMgr authMgr;
    @Override
    public int priority() {
        return 2;
    }

    @Override
    public ApiRsp handle(ApiReq inputMessage, ApiInterceptorChain chain) throws ApiException, ApiException {
        ApiRunContext apiRunContext = apiRunContextMgr.getApiRunContext(inputMessage.getApiName());
        if (apiRunContext.isIgnoreAuth()) {
            return chain.handle(inputMessage);
        }else {
            if (authMgr.checkAuth(inputMessage)) {
                return chain.handle(inputMessage);
            }else {
                return authMgr.response(inputMessage);
            }
        }
    }

}
