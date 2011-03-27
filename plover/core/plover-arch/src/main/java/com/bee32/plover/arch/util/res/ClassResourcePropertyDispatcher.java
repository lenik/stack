package com.bee32.plover.arch.util.res;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClassResourcePropertyDispatcher
        extends PropertyDispatcher {

    private Class<?> clazz;
    private Locale locale;

    public ClassResourcePropertyDispatcher(Class<?> clazz, Locale locale) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (locale == null)
            throw new NullPointerException("locale");

        this.clazz = clazz;
        this.locale = locale;
    }

    @Override
    public Object getBoundResource() {
        return clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        if (this.clazz == clazz)
            return;
        this.clazz = clazz;
        firePropertyRefresh(null);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        if (this.locale == locale)
            return;
        this.locale = locale;
        firePropertyRefresh(null);
    }

    @Override
    public void dispatch() {
        String baseName = clazz.getName();
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);

        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String content = resourceBundle.getString(key);
            dispatch(key, content);
        }
    }

}
