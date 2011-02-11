package com.bee32.plover.restful.pointer;

public interface IResourcePointer {

    /**
     * @param context
     *            Non-<code>null</code> context URL.
     * @return Non-<code>null</code> instantiated resource URL.
     */
    String instantiate(String context);

}
