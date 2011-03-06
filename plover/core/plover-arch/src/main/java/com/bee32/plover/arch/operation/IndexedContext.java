package com.bee32.plover.arch.operation;

public class IndexedContext
        extends OperationContext {

    private static final long serialVersionUID = 1L;

    private final Object[] parameters;

    public IndexedContext(Object... parameters) {
        if (parameters == null)
            throw new NullPointerException("parameters");

        this.parameters = parameters;

        for (int i = 0; i < parameters.length; i++)
            put(String.valueOf(i), parameters[i]);
    }

    public Object[] getArray() {
        return parameters;
    }

    @Override
    public Object get(int parameterIndex) {
        if (parameterIndex < parameters.length)
            return parameters[parameterIndex];
        return null;
    }

}
