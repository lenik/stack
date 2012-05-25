package com.bee32.plover.arch.util.res;

import java.util.Collections;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.free.ClassLocal;

public class ClassResourceProperties
        extends AbstractProperties {

    private ResourceBundle resourceBundle;

    ClassResourceProperties(Class<?> clazz) {
        this(clazz, Locale.getDefault());
    }

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
        else
            try {
                return resourceBundle.getString(key);
            } catch (MissingResourceException e) {
                return null;
            }
    }

    @Override
    public Set<String> keySet() {
        if (resourceBundle == null)
            return Collections.emptySet();
        else
            return resourceBundle.keySet();
    }

    static ClassLocal<ClassResourceProperties> instances = new ClassLocal<>();

    public static ClassResourceProperties getInstance(Class<?> clazz) {
        ClassResourceProperties instance = instances.get(clazz);
        if (instance == null) {
            // Locale defaultLocale = Locale.getDefault();
            instance = new ClassResourceProperties(clazz/* , defaultLocale */);
            instances.put(clazz, instance);
        }
        return instance;
    }

}
