package com.bee32.plover.arch.util.res;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;

public class NlsBundleEx {

    public static Map<String, String> load(Class<?> clazz) {
        Map<String, String> properties = new HashMap<String, String>();
        load(clazz, properties);
        return properties;
    }

    public static void load(Class<?> clazz, Map<String, String> properties) {
        load(clazz, null, properties, null);
    }

    /**
     * Load properties from all resource bundles for each class in the inheritance chain.
     *
     * The properties defined for superclass are overrided by the properties defined for subclasses
     * with the same name.
     *
     * @param clazz
     *            The start class whose resource bundle to be loaded.
     * @param baseSuffix
     *            The suffix name to the full qualified class name, which is used to construct the
     *            base name which is then referred to the resource bundle.
     * @param properties
     *            The output properties.
     * @param keyPrefix
     *            Only resource properties starts with the keyPrefix are loaded, and the keyPrefix
     *            is stripped from the key name.
     */
    public static void load(Class<?> clazz, String baseSuffix, Map<String, String> properties, String keyPrefix) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        if (properties == null)
            throw new NullPointerException("properties");

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null)
            load(superclass, baseSuffix, properties, keyPrefix);

        String baseName = clazz.getName() + baseSuffix;
        if (baseSuffix != null)
            baseName += baseSuffix;

        INlsBundle bundle;
        try {
            bundle = NlsBundles.getBundle(baseName);
        } catch (MissingResourceException e) {
            // skip intermediates which don't have a resource bundle.
            return;
        }

        Enumeration<String> keys = bundle.getKeys();
        while (keys.hasMoreElements()) {
            String _key = keys.nextElement();
            String key;
            if (keyPrefix != null)
                if (!_key.startsWith(keyPrefix))
                    continue;
                else
                    key = _key.substring(keyPrefix.length());
            else
                key = _key;

            String value = bundle.getString(_key);
            properties.put(key, value);
        }
    }

}
