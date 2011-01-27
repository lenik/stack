package com.bee32.plover.inject;

import java.util.HashMap;
import java.util.Map;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.Component;

public abstract class AbstractContainer
        extends Component
        implements IContainer {

    protected IContextManager contextManager = new ContextManager();

    public AbstractContainer() {
        super();
    }

    public AbstractContainer(String name) {
        super(name);
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

    // contextManager delegates.

    public final <T> T require(Class<T> contextClass)
            throws ContextException {
        return contextManager.require(contextClass);
    }

    public final <T> T require(Class<T> contextClass, Object qualifier)
            throws ContextException {
        return contextManager.require(contextClass, qualifier);
    }

    public final <T> T require(Class<T> contextClass, String qualifier)
            throws ContextException {
        return contextManager.require(contextClass, qualifier);
    }

    public final <T> T query(Class<T> contextClass)
            throws ContextException {
        return contextManager.query(contextClass);
    }

    public final <T> T query(Class<T> contextClass, Object qualifier)
            throws ContextException {
        return contextManager.query(contextClass, qualifier);
    }

    public final <T> T query(Class<T> contextClass, String qualifier)
            throws ContextException {
        return contextManager.query(contextClass, qualifier);
    }

    public final <T> void registerContext(Class<T> contextClass, T contextInstance) {
        contextManager.registerContext(contextClass, contextInstance);
    }

    public final <T> void registerContext(Class<T> contextClass, String qualifier, T contextInstance) {
        contextManager.registerContext(contextClass, qualifier, contextInstance);
    }

    public final <T> void registerContext(Class<T> contextClass, Object qualifier, T contextInstance) {
        contextManager.registerContext(contextClass, qualifier, contextInstance);
    }

    public final void removeContext(Class<?> contextClass) {
        contextManager.removeContext(contextClass);
    }

    public final void removeContext(Class<?> contextClass, String qualifier) {
        contextManager.removeContext(contextClass, qualifier);
    }

    public final void removeContext(Class<?> contextClass, Object qualifier) {
        contextManager.removeContext(contextClass, qualifier);
    }

    public final void removeContextInstances(Object contextInstance) {
        contextManager.removeContextInstances(contextInstance);
    }

}
