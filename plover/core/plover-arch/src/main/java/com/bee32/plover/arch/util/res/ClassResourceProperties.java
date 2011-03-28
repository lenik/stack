package com.bee32.plover.arch.util.res;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class ClassResourceProperties
        extends AbstractProperties {

    private ResourceBundle resourceBundle;

    public ClassResourceProperties(Class<?> clazz, Locale locale) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (locale == null)
            throw new NullPointerException("locale");

        String baseName = clazz.getName();
        this.resourceBundle = ResourceBundle.getBundle(baseName, locale);
    }

    @Override
    public String get(String key) {
        return resourceBundle.getString(key);
    }

    @Override
    public Set<String> keySet() {
        return resourceBundle.keySet();
    }

}
