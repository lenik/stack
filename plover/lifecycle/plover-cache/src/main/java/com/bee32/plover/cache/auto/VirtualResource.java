package com.bee32.plover.cache.auto;

/**
 * Tag class to annotate the derived classes are for virtual purpose.
 */
public abstract class VirtualResource<T>
        extends Resource<T> {

    public VirtualResource(IMakeSchema cacheSchema) {
        super(cacheSchema);
    }

}
