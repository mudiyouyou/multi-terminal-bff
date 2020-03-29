package net.multi.terminal.bff.core.apimgr;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Api实际方法运行上下文
 * 实际方法即为标注@ApiMapping的方法
 */
public class ApiRunContext {
    private final Class argType;
    private final Method method;
    private final Object instance;
    private final boolean ignoreAuth;
    private final boolean signable;
    public ApiRunContext(Method method, Object instance, Class argType, boolean ignoreAuth,boolean signable) {
        this.argType = argType;
        this.method = method;
        this.instance = instance;
        this.ignoreAuth = ignoreAuth;
        this.signable = signable;
    }

    public Type getArgType() {
        return argType;
    }

    public Method getMethod() {
        return method;
    }

    public Object getInstance() {
        return instance;
    }

    public boolean isIgnoreAuth() {
        return ignoreAuth;
    }
}
