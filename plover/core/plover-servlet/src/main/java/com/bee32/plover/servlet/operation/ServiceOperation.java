package com.bee32.plover.servlet.operation;

import java.lang.reflect.Method;

import com.bee32.plover.arch.operation.AbstractOperation;

public class ServiceOperation
        extends AbstractOperation {

    private final Method method;

    public ServiceOperation(Method method) {
        if (method == null)
            throw new NullPointerException("method");
        this.method = method;
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public Object execute(Object instance, Object... parameters)
            throws Exception {
        return method.invoke(instance, parameters);
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return method.getParameterTypes();
    }

}
