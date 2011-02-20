package com.bee32.plover.arch.locator;

public class LocationLookup {

    private String location;
    private Object object;
    private LocationLookup inner;

    public LocationLookup(String location, Object object, LocationLookup inner) {
        this.location = location;
        this.object = object;
        this.inner = inner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public LocationLookup getInner() {
        return inner;
    }

    public void setInner(LocationLookup inner) {
        this.inner = inner;
    }

    private void dumpFullLocation(StringBuffer buf) {
        if (location == null) {
            assert inner != null;
            inner.dumpFullLocation(buf);
        } else {
            buf.append(location);
            if (inner != null) {
                buf.append('/');
                inner.dumpFullLocation(buf);
            }
        }
    }

    public String joinLocation() {
        StringBuffer buf = new StringBuffer();
        dumpFullLocation(buf);
        return buf.toString();
    }

    @Override
    public String toString() {
        return joinLocation();
    }

}
