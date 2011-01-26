package com.bee32.plover.inject;

import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;
import javax.free.NotImplementedException;

public abstract class AbstractContainer
        extends ContextManager
        implements IContainer {

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
    public final <T> void registerContext(Class<T> contextClass, T contextInstance) {
        contextMap.put(contextClass, null, contextInstance);
    }

    @Override
    public <T> void registerContext(Class<T> contextClass, String qualifier, T contextInstance) {
        contextMap.put(contextClass, qualifier, contextInstance);
    }

    @Override
    public <T> void registerContext(Class<T> contextClass, Object qualifier, T contextInstance) {
        // contextMap.put(contextClass, qualifier, contextInstance);
        throw new NotImplementedException();
    }

    @Override
    public void removeContext(Class<?> contextClass, String qualifier) {
        contextMap.remove(contextClass);
    }

    @Override
    public void removeContext(Class<?> contextClass, Object qualifier) {
        // contextMap.remove(contextClass, qualifier);
        throw new NotImplementedException();
    }

    @Override
    public void removeContextInstances(Object contextInstance) {
        throw new NotImplementedException();
    }

    @Override
    public Object getFrameAttribute(Object key) {
        Object frame = getFrame();
        if (frame == null)
            return null;

        if (!(frame instanceof Map<?, ?>))
            throw new IllegalUsageException("Frame is already set to a non-Map object");

        Map<?, ?> mapFrame = (Map<?, ?>) frame;
        return mapFrame.get(key);
    }

    @Override
    public void setFrameAttribute(Object key, Object value) {
        Map<Object, Object> mapFrame = null;

        Object frame = getFrame();

        if (frame instanceof Map<?, ?>) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> _map = (Map<Object, Object>) frame;
            mapFrame = _map;
        }

        else if (frame == null) {
            mapFrame = new HashMap<Object, Object>();
            setFrame(mapFrame);
        }

        else
            throw new IllegalUsageException("Frame is already set to a non-Map object");

        mapFrame.put(key, value);
    }

}
