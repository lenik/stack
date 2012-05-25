package com.bee32.plover.arch.util.res;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

public class ResourceBundleProperties
        extends AbstractProperties {

    private ResourceBundle resourceBundle;

    public ResourceBundleProperties(ResourceBundle resourceBundle) {
        if (resourceBundle == null)
            throw new NullPointerException("resourceBundle");
        this.resourceBundle = resourceBundle;
    }

    @Override
    public String get(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    @Override
    public Set<String> keySet() {
        return resourceBundle.keySet();
    }

}
