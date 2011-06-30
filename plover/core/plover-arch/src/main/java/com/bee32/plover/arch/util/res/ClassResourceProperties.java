package com.bee32.plover.arch.util.res;

import java.util.Collections;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

public class ClassResourceProperties
        extends AbstractProperties {

    private ResourceBundle resourceBundle;

    public ClassResourceProperties(Class<?> clazz, Locale locale)
            throws MissingResourceException {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (locale == null)
            throw new NullPointerException("locale");

        String baseName = clazz.getName();

        try {
            this.resourceBundle = ResourceBundleUTF8.getBundle(baseName, locale);
        } catch (MissingResourceException e) {
            this.resourceBundle = null;
        }
    }

    @Override
    public String get(String key) {
        if (resourceBundle == null)
            return null;
        return resourceBundle.getString(key);
    }

    @Override
    public Set<String> keySet() {
        if (resourceBundle == null)
            return Collections.emptySet();
        return resourceBundle.keySet();
    }

}
