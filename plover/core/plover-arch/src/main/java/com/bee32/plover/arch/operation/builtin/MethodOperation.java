package com.bee32.plover.arch.operation.builtin;

import java.lang.reflect.Method;

import com.bee32.plover.arch.operation.AbstractOperation;

public class MethodOperation
        extends AbstractOperation {

    private final Method method;

    public MethodOperation(Method method) {
        if (method == null)
            throw new NullPointerException("method");
        this.method = method;
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

    @Override
    public Object execute(Object instance, Object... parameters)
            throws Exception {
        return method.invoke(instance, parameters);
    }

}
