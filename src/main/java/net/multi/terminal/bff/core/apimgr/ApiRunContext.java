package net.multi.terminal.bff.core.apimgr;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Api实际方法运行上下文
 * 实际方法即为标注@ApiMapping的方法
 */
public class ApiRunContext {
    private final Class argType;
    private final MethodHandle method;
    private final Object instance;
    private final boolean ignoreAuth;
    private final boolean signable;
    public ApiRunContext(MethodHandle method, Object instance, Class argType, boolean ignoreAuth,boolean signable) {
        this.argType = argType;
        this.method = method;
        this.instance = instance;
        this.ignoreAuth = ignoreAuth;
        this.signable = signable;
    }

    public Class getArgType() {
        return argType;
    }

    public MethodHandle getMethodHandle() {
        return method;
    }

    public Object getInstance() {
        return instance;
    }

    public boolean isIgnoreAuth() {
        return ignoreAuth;
    }
}
