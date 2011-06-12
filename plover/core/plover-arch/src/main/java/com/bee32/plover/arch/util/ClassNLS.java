package com.bee32.plover.arch.util;

import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

import javax.free.NLS;
import javax.free.ResourceBundleNLS;

public class ClassNLS {

    final Class<?> clazz;
    // ResourceBundle rb;
    final NLS nls;

    ClassNLS(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        this.clazz = clazz;

        String baseName = clazz.getName().replace('.', '/');
        // XXX Locale locale = Locale.getDefault(); // XXX

        // ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
        NLS nls;
        try {
            nls = new ResourceBundleNLS(null, baseName);
        } catch (MissingResourceException e) {
            nls = EmptyNLS.INSTANCE;
        }
        this.nls = nls;
    }

    public String getLabel() {
        String label = nls.getString("label", clazz.getSimpleName());
        return label;
    }

    public String getPropertyLabel(String propertyName) {
        String labelKey = propertyName + ".label";
        String propertyLabel = nls.getString(labelKey, propertyName);
        return propertyLabel;
    }

    static final Map<Class<?>, ClassNLS> classmap;
    static {
        classmap = new HashMap<Class<?>, ClassNLS>();
    }

    public static synchronized ClassNLS getNLS(Class<?> clazz) {
        ClassNLS nls = classmap.get(clazz);
        if (nls == null) {
            nls = new ClassNLS(clazz);
            classmap.put(clazz, nls);
        }
        return nls;
    }

}
