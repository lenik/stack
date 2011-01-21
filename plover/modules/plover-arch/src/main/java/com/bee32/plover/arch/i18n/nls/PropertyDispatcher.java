package com.bee32.plover.arch.i18n.nls;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Map.Entry;

public abstract class PropertyDispatcher {

    protected abstract void dispatch(String key, String content);

    public void visit(ResourceBundle resourceBundle) {
        if (resourceBundle == null)
            throw new NullPointerException("resourceBundle");
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String content = resourceBundle.getString(key);
            dispatch(key, content);
        }
    }

    public void visit(Properties properties) {
        if (properties == null)
            throw new NullPointerException("properties");
        for (Entry<Object, Object> entry : properties.entrySet()) {
            String key = String.valueOf(entry.getKey());
            String content = properties.getProperty(key);
            dispatch(key, content);
        }
    }

    public void visitClassResource(Class<?> clazz, Locale locale) {
        String baseName = clazz.getName();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        visit(resourceBundle);
    }

}
