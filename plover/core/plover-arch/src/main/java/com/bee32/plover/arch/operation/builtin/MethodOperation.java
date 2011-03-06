package com.bee32.plover.arch.operation.builtin;

import java.lang.reflect.Method;

import com.bee32.plover.arch.operation.AbstractOperation;
import com.bee32.plover.arch.operation.IOperationContext;

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
    public Object execute(Object instance, IOperationContext parameters)
            throws Exception {
        Class<?>[] tv = method.getParameterTypes();
        Object[] pv = new Object[tv.length];

        for (int i = 0; i < tv.length; i++)
            pv[i] = parameters.get(i);

        return method.invoke(instance, pv);
    }

}
