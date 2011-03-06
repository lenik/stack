package com.bee32.plover.arch.operation;

import java.io.Serializable;

public class OperationContext
        extends AbstractOperationContext
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public OperationContext(Object... parameters) {
        for (int i = 0; i < parameters.length; i++)
            put(String.valueOf(i), parameters[i]);
    }

    @Override
    public Object get(int parameterIndex) {
        return get(String.valueOf(parameterIndex));
    }

}
