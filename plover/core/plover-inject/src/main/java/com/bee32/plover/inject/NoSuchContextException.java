package com.bee32.plover.inject;

public class NoSuchContextException
        extends ContextException {

    private static final long serialVersionUID = 1L;

    private final Class<?> contextClass;

    public NoSuchContextException(Class<?> contextClass, Throwable cause) {
        super(contextClass.getName(), cause);
        this.contextClass = contextClass;
    }

    public NoSuchContextException(Class<?> contextClass) {
        super(contextClass.getName());
        this.contextClass = contextClass;
    }

    public Class<?> getContextClass() {
        return contextClass;
    }

}
