package net.multi.terminal.bff.core.apimgr;

import io.netty.handler.codec.http.HttpResponseStatus;
import net.multi.terminal.bff.constant.MsgCode;
import net.multi.terminal.bff.core.annotation.Api;
import net.multi.terminal.bff.core.annotation.ApiMapping;
import net.multi.terminal.bff.core.annotation.IgnoreAuth;
import net.multi.terminal.bff.core.annotation.Signable;
import net.multi.terminal.bff.exception.SystemException;
import net.multi.terminal.bff.model.ApiReq;
import net.multi.terminal.bff.model.ApiRsp;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApiRunContextMgr implements ApplicationContextAware {
    private ApplicationContext context;
    private Map<String, ApiRunContext> apiRunContextMap = new HashMap<>();

    @PostConstruct
    public void preHandle() throws SystemException {
        String[] beanNames = context.getBeanNamesForAnnotation(Api.class);
        for (String name : beanNames) {
            Object bean = context.getBean(name);
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(ApiMapping.class)) {
                    ApiMapping apiMapping = method.getAnnotation(ApiMapping.class);
                    validate(bean, method, apiMapping);
                    boolean ignoreAuth = checkIgnoreAuth(method);
                    boolean signable = checkSignable(method);
                    apiRunContextMap.put(apiMapping.name(), new ApiRunContext(method, bean, method.getParameterTypes()[0], ignoreAuth, signable));
                }
            }
        }
    }

    private boolean checkIgnoreAuth(Method method) {
        if (method.isAnnotationPresent(IgnoreAuth.class)) {
            return true;
        }
        return false;
    }

    private boolean checkSignable(Method method) {
        if (method.isAnnotationPresent(Signable.class)) {
            return true;
        }
        return false;
    }

    private void validate(Object bean, Method method, ApiMapping apiMapping) throws SystemException {
        if (apiMapping.name() == null || "".equals(apiMapping.name())) {
            throw new SystemException(
                    String.format("违反ApiMapping注解规则:%s中的%s方法上没有设置api名称", bean.getClass().getSimpleName(), method.getName()));
        }
        if (method.getParameterCount() != 1) {
            throw new SystemException(
                    String.format("违反ApiMapping注解规则:%s中的%s方法只能有一个参数", bean.getClass().getSimpleName(), method.getName()));
        }
        if (!method.getParameterTypes()[0].getSuperclass().equals(ApiReq.class) && !method.getParameterTypes()[0].equals(ApiReq.class)) {
            throw new SystemException(
                    String.format("违反ApiMapping注解规则:%s中的%s方法入参类型只能为ApiReq子类", bean.getClass().getSimpleName(), method.getName()));
        }
        if (!method.getReturnType().getSuperclass().equals(ApiRsp.class) && !method.getReturnType().equals(ApiRsp.class)) {
            throw new SystemException(
                    String.format("违反ApiMapping注解规则:%s中的%s方法返回值类型只能为ApiRsp子类", bean.getClass().getSimpleName(), method.getName()));
        }
    }

    public ApiRunContext getApiRunContext(String apiName) throws SystemException {
        ApiRunContext runContext = apiRunContextMap.get(apiName);
        if (runContext == null) {
            throw new SystemException(MsgCode.E_11003, HttpResponseStatus.BAD_REQUEST);
        }
        return runContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
