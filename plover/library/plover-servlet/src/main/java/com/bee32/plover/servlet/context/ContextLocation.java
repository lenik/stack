package com.bee32.plover.servlet.context;

import java.io.Serializable;

import javax.free.Nullables;

public class ContextLocation
        implements LocationContextConstants, Serializable {

    private static final long serialVersionUID = 1L;

    private LocationContext context;
    private String location;

    public ContextLocation(String location) {
        this(REQUEST, location);
    }

    public ContextLocation(LocationContext context, String location) {
        if (context == null)
            throw new NullPointerException("context");
        if (location == null)
            throw new NullPointerException("location");
        this.context = context;
        this.location = location;
    }

    public LocationContext getContext() {
        return context;
    }

    public void setContext(LocationContext context) {
        if (context == null)
            throw new NullPointerException("context");
        this.context = context;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location == null)
            throw new NullPointerException("location");
        this.location = location;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((context == null) ? 0 : context.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass())
            return false;

        ContextLocation other = (ContextLocation) obj;

        if (!context.equals(other.context))
            return false;

        if (!Nullables.equals(location, other.location))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return context + " :: " + location;
    }

}
