package com.bee32.plover.restful.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class ResourceDispatcher {

    private final Map<String, ResourceFactory> resourceTypes;

    public ResourceDispatcher() {
        this.resourceTypes = new HashMap<String, ResourceFactory>();
    }

    public void register(String type, ResourceFactory factory) {
        if (type == null)
            throw new NullPointerException("type");

        if (factory == null)
            throw new NullPointerException("factory");

        resourceTypes.put(type, factory);
    }

    public IResource dispatch(String mixedPath) {
        if (mixedPath == null)
            throw new NullPointerException("path");

        String type;
        String path;
        int slash = mixedPath.indexOf('/');
        if (slash != -1) {
            type = mixedPath.substring(0, slash);
            path = mixedPath.substring(slash); // with the leading /
        } else {
            type = mixedPath;
            path = "/";
        }
        return dispatch(type, path);
    }

    public IResource dispatch(String type, String path) {
        ResourceFactory resourceFactory = resourceTypes.get(type);
        if (resourceFactory == null)
            return null;

        return resourceFactory.resolve(path);
    }

    private static final ResourceDispatcher instance;
    static {
        instance = new ResourceDispatcher();

        // Load standards
        instance.register("crt", ClassResourceFactory.getInstance());

        // Load services.
        for (ResourceFactory factory : ServiceLoader.load(ResourceFactory.class)) {
            String type = factory.getType();
            instance.register(type, factory);
        }
    }

    public static ResourceDispatcher getInstance() {
        return instance;
    }

}
