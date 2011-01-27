package com.bee32.plover.inject;

public abstract class AbstractContextManager
        implements IContextManager {

    @Override
    public final <T> T require(Class<T> contextClass)
            throws ContextException {
        T context = query(contextClass);
        if (context == null)
            throw new NoSuchContextException(contextClass);
        return context;
    }

    @Override
    public final <T> T require(Class<T> contextClass, String qualifier)
            throws ContextException {
        T context = query(contextClass, qualifier);
        if (context == null)
            throw new NoSuchContextException(contextClass);
        return context;
    }

    @Override
    public final <T> T require(Class<T> contextClass, Object qualifier)
            throws ContextException {
        T context = query(contextClass, qualifier);
        if (context == null)
            throw new NoSuchContextException(contextClass);
        return context;
    }

}
