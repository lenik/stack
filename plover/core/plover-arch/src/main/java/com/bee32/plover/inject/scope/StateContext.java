package com.bee32.plover.inject.scope;

import java.util.Map;
import java.util.TreeMap;

public class StateContext {

    Map<String, Object> attributes;

    public StateContext() {
        attributes = new TreeMap<String, Object>();
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public boolean containsAttribute(String name) {
        return attributes.containsKey(name);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object removeAttribute(String name) {
        return attributes.remove(name);
    }

}
