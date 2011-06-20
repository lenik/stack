package com.bee32.icsf.access.resource;

import java.util.Collection;

/**
 * Resource namespace.
 */
public interface IResourceNamespace {

    /**
     * Get resource namespace.
     *
     * @return Non-<code>null</code> resource namespace.
     */
    String getNamespace();

    /**
     * The resource type used in this namespace.
     *
     * @return Resource type.
     */
    Class<? extends Resource> getResourceType();

    /**
     * Get resource by local name, the namespace is implied in {@link #getNamespace()}.
     *
     * @return Resource object for ths specific local name. Returns <code>null</code> if the
     *         resource isn't existed.
     */
    Resource getResource(String localName);

    /**
     * Get all resources defined in this namespace.
     *
     * @return A collection of resources defined by this namespace, never be <code>null</code>.
     */
    Collection<? extends Resource> getResources();

}
