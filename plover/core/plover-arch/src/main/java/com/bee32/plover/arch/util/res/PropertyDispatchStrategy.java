package com.bee32.plover.arch.util.res;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.free.SystemCLG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertyDispatchStrategy
        implements IPropertyDispatchStrategy, IPropertyBinding {

    static Logger logger = LoggerFactory.getLogger(PropertyDispatchStrategy.class);

    @Override
    public PropertyDispatcher bind(Properties properties) {
        return new BoundWithProperties(properties);
    }

    @Override
    public PropertyDispatcher bind(ResourceBundle resourceBundle) {
        return bind(resourceBundle, SystemCLG.locale.get());
    }

    @Override
    public PropertyDispatcher bind(ResourceBundle resourceBundle, Locale locale) {
        return new BoundWithResourceBundle(resourceBundle, locale);
    }

    @Override
    public PropertyDispatcher bind(Class<?> baseClass) {
        return bind(baseClass, SystemCLG.locale.get());
    }

    @Override
    public PropertyDispatcher bind(Class<?> baseClass, Locale locale) {
        return new BoundWithClassResource(baseClass, locale);
    }

    public abstract class BoundPropertyDispatcher
            extends PropertyDispatcher {

        public BoundPropertyDispatcher() {
            super(PropertyDispatchStrategy.this);

            Map<String, ? extends IPropertyAcceptor> acceptorMap = getAcceptorMap();

            if (acceptorMap != null) {
                for (Entry<String, ? extends IPropertyAcceptor> acceptorEntry : acceptorMap.entrySet()) {
                    String name = acceptorEntry.getKey();
                    IPropertyAcceptor acceptor = acceptorEntry.getValue();
                    setPropertyDispatcher(name, acceptor);
                }

            } else {

                Iterable<IPropertyAcceptor> acceptors = getAcceptors();
                for (IPropertyAcceptor acceptor : acceptors)
                    setPropertyDispatcher(null, acceptor);
            }
        }

        protected void setPropertyDispatcher(String name, IPropertyAcceptor acceptor) {
            String acceptorStr = acceptor.toString();
            if (name != null)
                acceptorStr = name + ":" + acceptorStr;

            if (acceptor instanceof IPropertyDispatcherAware) {
                // logger.debug("set-dispatcher to " + this + " on " + acceptorStr);

                IPropertyDispatcherAware aware = (IPropertyDispatcherAware) acceptor;
                aware.setPropertyDispatcher(this);
            } else {
                // logger.debug("skip-dispatcher to " + this + " on " + acceptorStr);
            }
        }

    }

    public class BoundWithProperties
            extends BoundPropertyDispatcher {

        Properties properties;

        public BoundWithProperties(Properties properties) {
            if (properties == null)
                throw new NullPointerException("properties");

            this.properties = properties;
        }

        public Properties getProperties() {
            return properties;
        }

        public BoundWithProperties setProperties(Properties properties) {
            this.properties = properties;
            return this;
        }

        @Override
        public Object getBoundResource() {
            return properties;
        }

        @Override
        public void dispatch() {
            if (properties == null)
                throw new IllegalStateException("properties is null");
            for (Entry<Object, Object> entry : properties.entrySet()) {
                String key = String.valueOf(entry.getKey());
                String content = properties.getProperty(key);
                strategy.dispatch(key, content);
            }
        }
    }

    public class BoundWithResourceBundle
            extends BoundPropertyDispatcher {

        ResourceBundle resourceBundle;
        Locale locale;

        public BoundWithResourceBundle(ResourceBundle resourceBundle, Locale locale) {
            this.resourceBundle = resourceBundle;
            this.locale = locale;
        }

        public ResourceBundle getResourceBundle() {
            return resourceBundle;
        }

        public BoundWithResourceBundle setResourceBundle(ResourceBundle resourceBundle) {
            this.resourceBundle = resourceBundle;
            return this;
        }

        public Locale getLocale() {
            return locale;
        }

        public BoundWithResourceBundle setLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        @Override
        public Object getBoundResource() {
            return resourceBundle;
        }

        @Override
        public void dispatch() {
            if (resourceBundle == null)
                throw new IllegalStateException("resourceBundle is null");

            Enumeration<String> keys = resourceBundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String content = resourceBundle.getString(key);
                strategy.dispatch(key, content);
            }
        }

    }

    public class BoundWithClassResource
            extends BoundWithResourceBundle {

        Class<?> baseClass;
        Locale locale;

        public BoundWithClassResource(Class<?> baseClass, Locale locale) {
            super(null, locale);
            this.baseClass = baseClass;
            this.locale = locale;
        }

        public Class<?> getBaseClass() {
            return baseClass;
        }

        public BoundWithClassResource setBaseClass(Class<?> baseClass) {
            this.baseClass = baseClass;
            return this;
        }

        public Locale getLocale() {
            return locale;
        }

        public BoundWithClassResource setLocale(Locale locale) {
            this.locale = locale;
            return this;
        }

        @Override
        public Object getBoundResource() {
            return baseClass;
        }

        @Override
        public void dispatch() {
            if (baseClass == null)
                throw new IllegalStateException("baseClass is null");
            if (locale == null)
                throw new IllegalStateException("locale is null");

            String baseName = baseClass.getName();
            ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);

            super.resourceBundle = resourceBundle;
            super.dispatch();
        }
    }

}
