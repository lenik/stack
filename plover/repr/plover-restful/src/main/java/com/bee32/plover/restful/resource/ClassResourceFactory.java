package com.bee32.plover.restful.resource;

import java.net.URL;

public class ClassResourceFactory
        extends ResourceFactory {

    private ClassLoader loader;

    public ClassResourceFactory() {
        this.loader = ClassResourceFactory.class.getClassLoader();
    }

    public ClassResourceFactory(ClassLoader loader) {
        if (loader == null)
            throw new NullPointerException("loader");
        this.loader = loader;
    }

    @Override
    public String getType() {
        return "class";
    }

    @Override
    public IResource resolve(String path) {
        URL resource = loader.getResource(path);
        return new URLResource(resource);
    }

    private static final ClassResourceFactory instance;
    static {
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        instance = new ClassResourceFactory(systemLoader);
    }

    public static ClassResourceFactory getInstance() {
        return instance;
    }

}
