package net.multi.terminal.bff.core.interceptor;

import net.multi.terminal.bff.exception.BusinessException;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;

/**
 * Api拦截器
 */
public interface ApiInterceptor {
    /**
     * 决定拦截器的执行顺序，数字越低优先级越高
     *
     * @return
     */
    int priority();

    /**
     * 拦截器处理逻辑
     *
     * @param inputMessage
     * @param chain
     * @return
     * @throws BusinessException
     */
    ApiRsp handle(ApiReq inputMessage, ApiInterceptorChain chain) throws BusinessException, SystemException;
}
