package com.bee32.plover.arch.operation.builtin;

import java.lang.reflect.Method;

import com.bee32.plover.arch.operation.AbstractOperation;
import com.bee32.plover.arch.operation.IOperationContext;

public class DirectOperation
        extends AbstractOperation {

    private final Method method;

    public DirectOperation(String name, Method method) {
        super(name);
        if (method == null)
            throw new NullPointerException("method");
        this.method = method;
    }

    @Override
    public Object execute(Object instance, IOperationContext context)
            throws Exception {
        return method.invoke(instance, context);
    }

}
