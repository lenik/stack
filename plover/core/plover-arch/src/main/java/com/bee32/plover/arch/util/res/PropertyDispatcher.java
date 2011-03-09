package com.bee32.plover.arch.util.res;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;

public abstract class PropertyDispatcher {

    protected abstract void dispatch(String key, String content);

    public void dispatchResourceBundle(ResourceBundle resourceBundle) {
        if (resourceBundle == null)
            throw new NullPointerException("resourceBundle");
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String content = resourceBundle.getString(key);
            dispatch(key, content);
        }
    }

    public void dispatchProperties(Properties properties) {
        if (properties == null)
            throw new NullPointerException("properties");
        for (Entry<Object, Object> entry : properties.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String content = properties.getProperty(key);
            dispatch(key, content);
        }
    }

    public void dispatchClassResource(Class<?> clazz, Locale locale) {
        String baseName = clazz.getName();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        dispatchResourceBundle(resourceBundle);
    }

}
