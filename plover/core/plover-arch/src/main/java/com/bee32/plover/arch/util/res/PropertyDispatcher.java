package com.bee32.plover.arch.util.res;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;

import javax.free.IllegalUsageException;
import javax.free.PrefixMap;
import javax.free.StringPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyDispatcher
        implements IPropertyDispatcher {

    static Logger logger = LoggerFactory.getLogger(PropertyDispatcher.class);

    private boolean pulled;

    private IPropertyAcceptor rootAcceptor;
    private PrefixMap<IPropertyAcceptor> prefixAcceptors;

    private IProperties properties;

    private List<IPropertyRefreshListener> propertyRefreshListeners;

    public PropertyDispatcher(IProperties properties) {
        this.properties = properties;
    }

    public PropertyDispatcher(Class<?> baseClass) {
        this(new ClassResourceProperties(baseClass, Locale.getDefault()));
    }

    public PropertyDispatcher(Properties properties) {
        this(new MapProperties(properties));
    }

    public PropertyDispatcher(INlsBundle resourceBundle) {
        this(new NlsBundleProperties(resourceBundle));
    }

    @Override
    public IPropertyAcceptor getRootAcceptor() {
        return rootAcceptor;
    }

    @Override
    public PropertyDispatcher setRootAcceptor(IPropertyAcceptor rootAcceptor) {
        this.rootAcceptor = rootAcceptor;
        return this;
    }

    @Override
    public IPropertyAcceptor getPrefixAcceptor(String prefix) {
        if (prefixAcceptors == null)
            return null;
        return prefixAcceptors.get(prefix);
    }

    @Override
    public PropertyDispatcher addPrefixAcceptor(String prefix, IPropertyAcceptor acceptor) {
        if (acceptor == null)
            throw new NullPointerException("acceptor");

        if (prefix == null || prefix.isEmpty()) {
            setRootAcceptor(acceptor);
            return this;
        }

        if (prefixAcceptors == null)
            prefixAcceptors = new PrefixMap<IPropertyAcceptor>();

        if (prefixAcceptors.containsKey(prefix)) {
            IPropertyAcceptor _sink = prefixAcceptors.get(prefix);
            throw new IllegalUsageException(String.format("Prefix  %s is already registered with %s: %s", //
                    prefix, _sink.getClass().getSimpleName(), _sink));
        }

        prefixAcceptors.put(prefix, acceptor);
        return this;
    }

    public PropertyDispatcher addKeyAcceptor(String key, IPropertyAcceptor acceptor) {
        if (key == null || key.isEmpty())
            setRootAcceptor(acceptor);
        else
            addPrefixAcceptor(key + ".", acceptor);
        return this;
    }

    @Override
    public IPropertyAcceptor removePrefixAcceptor(String prefix) {
        if (prefixAcceptors == null)
            return null;
        return prefixAcceptors.remove(prefix);
    }

    @Override
    public IProperties getProperties() {
        if (properties == null)
            throw new IllegalStateException("Properties hasn't been set.");
        return properties;
    }

    @Override
    public void setProperties(IProperties properties) {
        if (properties == null)
            throw new NullPointerException("properties");
        this.properties = properties;
    }

    @Override
    public void dispatch() {
        if (properties == null)
            throw new IllegalStateException("Properties hasn't been set.");

        for (Entry<String, String> entry : properties) {
            String key = entry.getKey();
            String content = entry.getValue();
            dispatch(key, content);
        }
    }

    @Override
    public void dispatch(String key, String content) {
        if (prefixAcceptors != null) {
            String prefix = prefixAcceptors.floorKey(key);

            if (prefix != null && key.startsWith(prefix)) {
                IPropertyAcceptor acceptor = prefixAcceptors.get(prefix);

                String prefixStrippedKey = key.substring(prefix.length());
                acceptor.receive(prefixStrippedKey, content);

                return;
            }
        }

        if (rootAcceptor != null)
            rootAcceptor.receive(key, content);
    }

    @Override
    public void pull() {
        if (!pulled) {
            synchronized (this) {
                if (!pulled) {
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
