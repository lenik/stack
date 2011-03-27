package com.bee32.plover.arch.util.res;

import java.util.Map.Entry;
import java.util.Properties;

public class PropertiesPropertyDispatcher
        extends PropertyDispatcher {

    private Properties properties;

    public PropertiesPropertyDispatcher(Properties properties) {
        if (properties == null)
            throw new NullPointerException("properties");
        this.properties = properties;
    }

    @Override
    public Object getBoundResource() {
        return properties;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        if (this.properties == properties)
            return;

        if (properties == null)
            throw new NullPointerException("properties");

        this.properties = properties;

        firePropertyRefresh(null);
    }

    @Override
    public void dispatch() {
        for (Entry<Object, Object> entry : properties.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String content = properties.getProperty(key);
            dispatch(key, content);
        }
    }

}
