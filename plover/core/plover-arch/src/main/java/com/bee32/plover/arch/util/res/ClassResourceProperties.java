package com.bee32.plover.arch.util.res;

import java.util.Collections;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

import javax.free.ClassLocal;

public class ClassResourceProperties
        extends AbstractProperties {

    private INlsBundle nlsBundle;

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
            this.nlsBundle = NlsBundles.getBundle(baseName, locale);
        } catch (MissingResourceException e) {
            this.nlsBundle = null;
        }
    }

    @Override
    public String get(String key) {
        if (nlsBundle == null)
            return null;
        else
            try {
                return nlsBundle.getString(key);
            } catch (MissingResourceException e) {
                return null;
            }
    }

    @Override
    public Set<String> keySet() {
        if (nlsBundle == null)
            return Collections.emptySet();
        else
            return nlsBundle.keySet();
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
