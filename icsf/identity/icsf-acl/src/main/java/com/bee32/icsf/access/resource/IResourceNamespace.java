package com.bee32.icsf.access.resource;

public interface IResourceNamespace {

    String getNamespace();

    Class<? extends Resource> getResourceType();

    Resource getResource(String localName);

}
