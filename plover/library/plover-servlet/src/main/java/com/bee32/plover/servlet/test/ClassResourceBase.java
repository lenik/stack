package com.bee32.plover.servlet.test;

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
        return clazz.getResource(name);
    }

    @Override
    public String toString() {
        return clazz.getName();
    }

}
