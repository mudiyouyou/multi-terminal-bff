package net.multi.terminal.bff.core.interceptor;

import net.multi.terminal.bff.exception.BusinessException;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;

/**
 * Api拦截器的链对象，用于串联所有拦截器
 */
public class ApiInterceptorChain {
    private ApiInterceptor instance;
    private ApiInterceptorChain next;

    public ApiInterceptorChain(ApiInterceptor instance) {
        this.instance = instance;
    }

    public ApiRsp handle(ApiReq req) throws BusinessException, SystemException {
        return instance.handle(req, next);
    }

    public void setNext(ApiInterceptorChain next) {
        this.next = next;
    }
}
