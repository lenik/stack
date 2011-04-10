package com.bee32.icsf.access.resource;

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

}
