package com.bee32.plover.servlet.context;

import java.io.Serializable;

public final class LocationContext
        implements ILocationContext, Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public LocationContext(String name) {
        this.name = name;
    }

    @Override
    public ContextLocation resolve(String location) {
        if (location == null)
            throw new NullPointerException("location");

        return new ContextLocation(this, location);
    }

    @Override
    public String toString() {
        return "Context<" + name + ">";
    }

}
