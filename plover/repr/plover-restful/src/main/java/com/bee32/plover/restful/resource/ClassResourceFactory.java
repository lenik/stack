package com.bee32.plover.restful.resource;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

public class ClassResourceFactory
        extends ResourceFactory {

    private Map<String, ClassLoader> loaders;

    public ClassResourceFactory() {
        loaders = new TreeMap<String, ClassLoader>();

        loaders.put("scl", ClassLoader.getSystemClassLoader());
        // loaders.put("ccl", Thread.currentThread().getContextClassLoader());
        loaders.put("core", ClassResourceFactory.class.getClassLoader());
    }

    @Override
    public String getType() {
        return ResourceTypes.RT_CLASS;
    }

    @Override
    public IResource resolve(String path)
            throws ResourceResolveException {
        int slash = path.indexOf('/');
        if (slash == -1)
            throw new ResourceResolveException("Context class/loader for class-resource is missing");

        String contextName = path.substring(0, slash);
        path = path.substring(slash + 1);

        URL url;

        ClassLoader contextLoader = loaders.get(contextName);
        if (contextLoader != null) {
            url = contextLoader.getResource(path);
            if (url == null)
                throw new ResourceResolveException(String.format(
                        "Failed to resolve resource %s with-in class loader %s.", contextName, path));
        } else {
            Class<?> contextClass;
            try {
                contextClass = Class.forName(contextName);
            } catch (ClassNotFoundException e) {
                throw new ResourceResolveException(e.getMessage(), e);
            }
            url = contextClass.getResource(path);
            if (url == null)
                throw new ResourceResolveException(String.format("Failed to resolve resource %s relative to class %s.",
                        path, contextClass));
        }
        return new URLResource(url);
    }

}
