package net.multi.terminal.bff.core.apimgr;

import net.multi.terminal.bff.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Api名称到Api方法的路由逻辑
 * 通过请求中Api名称得到对应的Api方法上下文，封装到ApiInvoker中
 */
@Component
public class ApiMappingService  {
    @Autowired
    private ApiRunContextMgr apiRunContextMgr;

    public ApiInvoker route(String apiName) throws ApiException {
        ApiRunContext runContext = apiRunContextMgr.getApiRunContext(apiName);
        return new ApiInvoker(runContext);
    }

}
