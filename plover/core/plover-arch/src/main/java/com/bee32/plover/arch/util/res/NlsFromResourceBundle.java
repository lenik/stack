package com.bee32.plover.arch.util.res;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class NlsFromResourceBundle
        // extends AbstractProxy<ResourceBundle>
        implements INlsBundle {

    final ResourceBundle resourceBundle;

    public NlsFromResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public String getString(String key) {
        String message = resourceBundle.getString(key);
        message = NlsMessageProcessors.processMessage(message);
        return message;
    }

    @Override
    public String[] getStringArray(String key) {
        String[] src = resourceBundle.getStringArray(key);
        String[] dst = new String[src.length];
        for (int i = 0; i < src.length; i++) {
            dst[i] = NlsMessageProcessors.processMessage(src[i]);
        }
        return dst;
    }

    @Override
    public Object getObject(String key) {
        Object value = resourceBundle.getObject(key);
        if (value instanceof String) {
            String message = (String) value;
            message = NlsMessageProcessors.processMessage(message);
            return message;
        } else
            return value;
    }

    @Override
    public Locale getLocale() {
        return resourceBundle.getLocale();
    }

    @Override
    public Enumeration<String> getKeys() {
        return resourceBundle.getKeys();
    }

    @Override
    public boolean containsKey(String key) {
        return resourceBundle.containsKey(key);
    }

    @Override
    public Set<String> keySet() {
        return resourceBundle.keySet();
    }

}
