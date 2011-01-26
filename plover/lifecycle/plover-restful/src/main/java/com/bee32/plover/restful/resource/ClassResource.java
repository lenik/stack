package com.bee32.plover.restful.resource;

import java.io.IOException;
import java.io.InputStream;

public class ClassResource
        extends AbstractResource {

    private Class<?> clazz;
    private String name;

    public ClassResource(Class<?> clazz, String name) {
        if (clazz == null)
            throw new NullPointerException("clazz");

        this.clazz = clazz;
        this.name = name;
    }

    @Override
    public String getPath() {
        return name;
    }

    @Override
    public InputStream openBinary()
            throws IOException {
        InputStream in = clazz.getResourceAsStream(name);
        return in;
    }

}
