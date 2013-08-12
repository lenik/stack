package com.bee32.icsf.access.resource;

import java.util.Collection;

/**
 * 访问点名字空间
 */
public class AccessPointNS
        implements IResourceNamespace {

    @Override
    public String getNamespace() {
        return "ap";
    }

    @Override
    public Class<? extends Resource> getResourceType() {
        return AccessPoint.class;
    }

    @Override
    public Resource getResource(String localName) {
        return AccessPoint.getInstance(localName);
    }

    @Override
    public Collection<? extends Resource> getResources() {
        return AccessPoint.getInstances();
    }

    private static final AccessPointNS instance = new AccessPointNS();

    public static AccessPointNS getInstance() {
        return instance;
    }

}
