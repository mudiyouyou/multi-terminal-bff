package net.multi.terminal.bff.core.interceptor;

import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.exception.ApiException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 核心功能入口类
 * 用于初始化Api拦截器和调用拦截器链
 */
@Component
public class ApiInterceptorChainContext implements ApplicationContextAware {
    private ApiInterceptorChain first;
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        ArrayList<ApiInterceptor> interceptors = new ArrayList(context.getBeansOfType(ApiInterceptor.class).values());
        Collections.sort(interceptors, Comparator.comparingInt(ApiInterceptor::priority));
        ApiInterceptorChain previous = null;
        for (ApiInterceptor interceptor : interceptors) {
            ApiInterceptorChain next = new ApiInterceptorChain(interceptor);
            if (first == null) {
                first = next;
            } else {
                previous.setNext(next);
            }
            previous = next;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public ApiRsp handle(ApiReq req) throws ApiException, ApiException {
        return first.handle(req);
    }
}
