package com.bee32.plover.restful.resource;

public interface IResourceFactory {

    /**
     * Resolve the resource.
     *
     * @param path
     *            Non-<code>null</code> path, without the leading slash.
     */
    IResource resolve(String path);

}
