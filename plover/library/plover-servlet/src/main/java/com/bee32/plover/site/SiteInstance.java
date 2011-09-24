package com.bee32.plover.site;

import java.util.HashMap;
import java.util.Map;

public class SiteInstance {

    SiteConfig config;
    Map<String, Object> attributes = new HashMap<String, Object>();

    public SiteInstance(SiteConfig config) {
        if (config == null)
            throw new NullPointerException("config");
        this.config = config;
    }

    public String getName() {
        return config.getName();
    }

    public SiteConfig getConfig() {
        return config;
    }

    public void setConfig(SiteConfig config) {
        if (config == null)
            throw new NullPointerException("config");
        this.config = config;
    }

    public boolean containsAttribute(String attributeName) {
        return attributes.containsKey(attributeName);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Object getAttribute(String attributeName) {
        if (attributeName == null)
            throw new NullPointerException("attributeName");
        return attributes.get(attributeName);
    }

    public void setAttribute(String attributeName, Object attributeValue) {
        if (attributeName == null)
            throw new NullPointerException("attributeName");
        attributes.put(attributeName, attributeValue);
    }

    public Object removeAttribute(String attributeName) {
        return attributes.remove(attributeName);
    }

    @Override
    public String toString() {
        return getName();
    }

}
