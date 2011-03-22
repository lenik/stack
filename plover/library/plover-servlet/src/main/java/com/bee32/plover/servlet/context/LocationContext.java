package com.bee32.plover.servlet.context;

import java.io.Serializable;

public final class LocationContext
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;

    public LocationContext(String name) {
        this.name = name;
    }

    public ContextLocation resolve(String location) {
        if (location == null)
            throw new NullPointerException("location");

        return new ContextLocation(this, location);
    }

    @Override
    public String toString() {
        return "LocationContext [name=" + name + "]";
    }

    public static final LocationContext REQUEST = new LocationContext("request");
    public static final LocationContext SERVLET_CONTEXT = new LocationContext("servlet-context");

}
