package com.bee32.plover.inject;

import com.bee32.plover.arch.Component;

public class DelegatedContainer
        extends Component
        implements IContainer {

    private IContainer container;

    public DelegatedContainer(IContainer container) {
        super();
        setContainer(container);
    }

    public DelegatedContainer(String name, IContainer container) {
        super(name);
        setContainer(container);
    }

    protected IContainer getContainer() {
        return container;
    }

    protected void setContainer(IContainer container) {
        if (container == null)
            throw new NullPointerException("container");
        this.container = container;
    }

    @Override
    public <T> T require(Class<T> contextClass)
            throws ContextException {
        return container.require(contextClass);
    }

    @Override
    public <T> T require(Class<T> contextClass, Object qualifier)
            throws ContextException {
        return container.require(contextClass, qualifier);
    }

    @Override
    public <T> T require(Class<T> contextClass, String qualifier)
            throws ContextException {
        return container.require(contextClass, qualifier);
    }

    @Override
    public <T> T query(Class<T> contextClass)
            throws ContextException {
        return container.query(contextClass);
    }

    @Override
    public <T> T query(Class<T> contextClass, Object qualifier)
            throws ContextException {
        return container.query(contextClass, qualifier);
    }

    @Override
    public <T> T query(Class<T> contextClass, String qualifier)
            throws ContextException {
        return container.query(contextClass, qualifier);
    }

    @Override
    public <T> void registerContext(Class<T> contextClass, T contextInstance) {
        container.registerContext(contextClass, contextInstance);
    }

    @Override
    public <T> void registerContext(Class<T> contextClass, String qualifier, T contextInstance) {
        container.registerContext(contextClass, qualifier, contextInstance);
    }

    @Override
    public <T> void registerContext(Class<T> contextClass, Object qualifier, T contextInstance) {
        container.registerContext(contextClass, qualifier, contextInstance);
    }

    @Override
    public void removeContext(Class<?> contextClass) {
        container.removeContext(contextClass);
    }

    @Override
    public void removeContext(Class<?> contextClass, String qualifier) {
        container.removeContext(contextClass, qualifier);
    }

    @Override
    public void removeContext(Class<?> contextClass, Object qualifier) {
        container.removeContext(contextClass, qualifier);
    }

    @Override
    public void removeContextInstances(Object contextInstance) {
        container.removeContextInstances(contextInstance);
    }

    // IScoping stuff

    @Override
    public Object getFrame() {
        return container.getFrame();
    }

    @Override
    public void setFrame(Object frameObject) {
        container.setFrame(frameObject);
    }

    @Override
    public Object getFrameAttribute(Object key) {
        return container.getFrameAttribute(key);
    }

    @Override
    public void setFrameAttribute(Object key, Object value) {
        container.setFrameAttribute(key, value);
    }

}
