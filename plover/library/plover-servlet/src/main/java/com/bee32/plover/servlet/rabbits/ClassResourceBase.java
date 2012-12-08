package com.bee32.plover.servlet.rabbits;

import java.net.URL;

public class ClassResourceBase
        implements IResourceBase {

    private final Class<?> clazz;

    public ClassResourceBase(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");
        this.clazz = clazz;
    }

    @Override
    public URL getResource(String name) {
        // XXX Optim: 2235 inv. 1419 us
        return clazz.getResource(name);
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof ClassResourceBase))
            return false;
        ClassResourceBase o = (ClassResourceBase) obj;
        if (clazz != o.clazz)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return clazz.getName();
    }

}
