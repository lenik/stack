package com.bee32.plover.inject;

import com.bee32.plover.arch.Component;

public abstract class AbstractContextManager
        extends Component
        implements IContextManager {

    public AbstractContextManager() {
        super();
    }

    public AbstractContextManager(String name) {
        super(name);
    }

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
