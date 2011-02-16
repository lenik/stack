package com.bee32.sems.security.access;

import java.util.EventObject;

public class InvalidateEvent
        extends EventObject {

    private static final long serialVersionUID = 1L;

    private final Object key;

    public InvalidateEvent(Object source, Object key) {
        super(source);
        if (key == null)
            throw new NullPointerException("key");
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

}
