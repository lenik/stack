package com.bee32.plover.arch.operation.builtin;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.operation.AbstractOperation;
import com.bee32.plover.arch.operation.IParameterMap;

public class DelegatedMethodOperation
        extends AbstractOperation {

    private final Method method;
    private final Class<?>[] parameterTypes;

    public DelegatedMethodOperation(Method method) {
        if (method == null)
            throw new NullPointerException("method");

        int modifiers = method.getModifiers();
        if (!Modifier.isStatic(modifiers))
            throw new IllegalUsageException("Not a static method: " + method);

        Class<?>[] parameterTypes = method.getParameterTypes();

        if (parameterTypes.length < 1)
            throw new IllegalUsageException("The delegated method should have at least one parameter: " + method);

        this.parameterTypes = new Class<?>[parameterTypes.length - 1];
        System.arraycopy(parameterTypes, 1, this.parameterTypes, 0, parameterTypes.length - 1);

        this.method = method;

    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public Object execute(Object instance, IParameterMap parameters)
            throws Exception {
        Class<?>[] tv = method.getParameterTypes();
        Object[] pv = new Object[1 + tv.length];

        pv[0] = instance;

        for (int i = 0; i < tv.length; i++)
            pv[i + 1] = parameters.get(i);

        return method.invoke(null, pv);
    }

}
