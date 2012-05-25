package com.bee32.plover.arch.util.res;

import javax.free.IllegalUsageException;

public class PropertyDispatcherProxy
        implements IPropertyDispatcher {

    final IPropertyDispatcher proxy;

    public PropertyDispatcherProxy(IPropertyDispatcher proxy) {
        this.proxy = proxy;
    }

    @Override
    public IPropertyAcceptor getRootAcceptor() {
        return proxy.getRootAcceptor();
    }

    @Override
    public PropertyDispatcher setRootAcceptor(IPropertyAcceptor acceptor) {
        return proxy.setRootAcceptor(acceptor);
    }

    @Override
    public IPropertyAcceptor getPrefixAcceptor(String prefix) {
        return proxy.getPrefixAcceptor(prefix);
    }

    @Override
    public PropertyDispatcher addPrefixAcceptor(String prefix, IPropertyAcceptor acceptor)
            throws IllegalUsageException {
        return proxy.addPrefixAcceptor(prefix, acceptor);
    }

    @Override
    public IPropertyAcceptor removePrefixAcceptor(String prefix) {
        return proxy.removePrefixAcceptor(prefix);
    }

    @Override
    public void dispatch(String key, String content) {
        proxy.dispatch(key, content);
    }

    @Override
    public IProperties getProperties() {
        return proxy.getProperties();
    }

    @Override
    public void setProperties(IProperties properties) {
        proxy.setProperties(properties);
    }

    @Override
    public void dispatch() {
        proxy.dispatch();
    }

    @Override
    public void pull() {
        proxy.pull();
    }

    @Override
    public void addPropertyRefreshListener(IPropertyRefreshListener listener) {
        proxy.addPropertyRefreshListener(listener);
    }

    @Override
    public void removePropertyRefreshListener(IPropertyRefreshListener listener) {
        proxy.removePropertyRefreshListener(listener);
    }

}
