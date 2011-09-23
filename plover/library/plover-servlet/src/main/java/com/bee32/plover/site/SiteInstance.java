package com.bee32.plover.site;

import java.util.HashMap;
import java.util.Map;

public class SiteInstance {

    String name;
    Map<String, Object> attributes = new HashMap<String, Object>();

    public SiteInstance(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    public String getName() {
        return name;
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
        return "Site: " + name;
    }

}
