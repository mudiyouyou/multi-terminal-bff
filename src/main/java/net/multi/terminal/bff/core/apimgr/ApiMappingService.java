package net.multi.terminal.bff.core.apimgr;

import io.netty.handler.codec.http.HttpResponseStatus;
import net.multi.terminal.bff.constant.MsgCode;
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
        if (runContext == null) {
            throw new ApiException(MsgCode.E_11003, HttpResponseStatus.BAD_REQUEST);
        }
        return new ApiInvoker(runContext);
    }

}
