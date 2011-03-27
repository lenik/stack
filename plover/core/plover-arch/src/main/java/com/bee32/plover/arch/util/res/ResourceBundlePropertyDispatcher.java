package com.bee32.plover.arch.util.res;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class ResourceBundlePropertyDispatcher
        extends PropertyDispatcher {

    private ResourceBundle resourceBundle;

    public ResourceBundlePropertyDispatcher(ResourceBundle resourceBundle) {
        if (resourceBundle == null)
            throw new NullPointerException("resourceBundle");

        this.resourceBundle = resourceBundle;
    }

    @Override
    public Object getBoundResource() {
        return resourceBundle;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        if (this.resourceBundle == resourceBundle)
            return;

        this.resourceBundle = resourceBundle;
        this.firePropertyRefresh(null);
    }

    @Override
    public void dispatch() {
        if (resourceBundle == null)
            throw new IllegalStateException("resourceBundle is null");

        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String content = resourceBundle.getString(key);
            dispatch(key, content);
        }
    }

}
