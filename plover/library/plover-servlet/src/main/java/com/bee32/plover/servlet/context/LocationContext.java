package com.bee32.plover.servlet.context;

public final class LocationContext {

    private final String name;

    public LocationContext(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LocationContext [name=" + name + "]";
    }

    public static final LocationContext REQUEST = new LocationContext("request");
    public static final LocationContext SERVLET_CONTEXT = new LocationContext("servlet-context");

}
