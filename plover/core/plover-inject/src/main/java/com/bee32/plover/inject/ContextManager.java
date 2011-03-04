package com.bee32.plover.inject;

import javax.free.NotImplementedException;

import com.bee32.plover.inject.util.NameQualifiedClassMap;

public class ContextManager
        extends AbstractContextManager {

    protected final NameQualifiedClassMap<Object> contextMap;
    {
        contextMap = new NameQualifiedClassMap<Object>();
    }

    public ContextManager() {
    }

    public ContextManager(String name) {
        super(name);
    }

    @Override
    public <T> T query(Class<T> contextClass)
            throws ContextException {
        Object context = contextMap.floor(contextClass);
        return contextClass.cast(context);
    }

    @Override
    public <T> T query(Class<T> contextClass, String qualifier)
            throws ContextException {
        Object context = contextMap.floor(contextClass, qualifier);
        return contextClass.cast(context);
    }

    @Override
    public <T> T query(Class<T> contextClass, Object qualifier)
            throws ContextException {
        // Object context = contextMap.get(contextClass, qualifier);
        // return contextClass.cast(context);
        throw new NotImplementedException();
    }

    @Override
    public <T> void registerContext(Class<T> contextClass, T contextInstance) {
        contextMap.put(contextClass, contextInstance);
    }

    @Override
    public <T> void registerContext(Class<T> contextClass, String qualifier, T contextInstance) {
        contextMap.put(contextClass, qualifier, contextInstance);
    }

    @Override
    public <T> void registerContext(Class<T> contextClass, Object qualifier, T contextInstance) {
        throw new NotImplementedException();
    }

    @Override
    public void removeContext(Class<?> contextClass) {
        contextMap.remove(contextClass);
    }

    @Override
    public void removeContext(Class<?> contextClass, String qualifier) {
        contextMap.remove(contextClass, qualifier);
    }

    @Override
    public void removeContext(Class<?> contextClass, Object qualifier) {
        throw new NotImplementedException();
    }

    @Override
    public void removeContextInstances(Object contextInstance) {
        throw new NotImplementedException();
    }

}
