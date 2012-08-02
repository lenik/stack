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
        return resourceBundle.getString(key);
    }

    @Override
    public String[] getStringArray(String key) {
        return resourceBundle.getStringArray(key);
    }

    @Override
    public Object getObject(String key) {
        return resourceBundle.getObject(key);
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
