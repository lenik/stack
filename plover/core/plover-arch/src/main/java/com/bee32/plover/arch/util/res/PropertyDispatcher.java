package com.bee32.plover.arch.util.res;

import java.util.ArrayList;
import java.util.List;

import javax.free.IllegalUsageException;
import javax.free.PrefixMap;
import javax.free.StringPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertyDispatcher
        implements IPropertyDispatcher {

    static Logger logger = LoggerFactory.getLogger(PropertyDispatcher.class);

    private boolean required;

    private IPropertyAcceptor rootAcceptor;
    private PrefixMap<IPropertyAcceptor> prefixAcceptors;

    private List<IPropertyRefreshListener> propertyRefreshListeners;

    @Override
    public IPropertyAcceptor getRootAcceptor() {
        return rootAcceptor;
    }

    @Override
    public void setRootAcceptor(IPropertyAcceptor rootAcceptor) {
        this.rootAcceptor = rootAcceptor;
    }

    @Override
    public IPropertyAcceptor getPrefixAcceptor(String prefix) {
        return prefixAcceptors.get(prefix);
    }

    @Override
    public void addPrefixAcceptor(String prefix, IPropertyAcceptor acceptor) {
        if (prefix == null)
            throw new NullPointerException("prefix");
        if (acceptor == null)
            throw new NullPointerException("acceptor");

        if (prefix.isEmpty()) {
            setRootAcceptor(acceptor);
            return;
        }

        if (prefixAcceptors.containsKey(prefix)) {
            IPropertyAcceptor _sink = prefixAcceptors.get(prefix);
            throw new IllegalUsageException("Prefix " + prefix + " is already registered with " + _sink);
        }

        prefixAcceptors.put(prefix, acceptor);
    }

    @Override
    public IPropertyAcceptor removePrefixAcceptor(String prefix) {
        return prefixAcceptors.remove(prefix);
    }

    @Override
    public void dispatch(String key, String content) {
        String prefix = prefixAcceptors.floorKey(key);
        if (prefix == null || !key.startsWith(prefix)) {
            logger.debug("Skipped property: " + key);
            return;
        }

        IPropertyAcceptor acceptor = prefixAcceptors.get(prefix);
        assert acceptor != null : "already checked";

        String prefixStrippedKey = key.substring(prefix.length());
        acceptor.receive(prefixStrippedKey, content);
    }

    @Override
    public void require() {
        if (!required) {
            synchronized (this) {
                if (!required) {
                    dispatch();
                }
            }
        }
    }

    @Override
    public void addPropertyRefreshListener(IPropertyRefreshListener listener) {
        if (listener == null)
            throw new NullPointerException("listener");

        if (propertyRefreshListeners == null)
            propertyRefreshListeners = new ArrayList<IPropertyRefreshListener>(1);

        propertyRefreshListeners.add(listener);
    }

    @Override
    public void removePropertyRefreshListener(IPropertyRefreshListener listener) {
        if (listener == null)
            throw new NullPointerException("listener");

        if (propertyRefreshListeners != null)
            propertyRefreshListeners.remove(listener);
    }

    protected void firePropertyRefresh(String propertyName) {
        if (propertyRefreshListeners != null) {
            PropertyRefreshEvent event = new PropertyRefreshEvent(this, propertyName);

            for (IPropertyRefreshListener listener : propertyRefreshListeners)
                listener.propertyRefresh(event);
        }
    }

    @Override
    public String toString() {
        String dispatcherType = getClass().getSimpleName();

        if (dispatcherType.contains("$"))
            dispatcherType = StringPart.afterLast(dispatcherType, '$');

        return dispatcherType;
    }

}
