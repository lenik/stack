package com.bee32.plover.cache.auto;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Normal resource can have dependencies.
 */
public abstract class Resource<T>
        extends BaseResource<T> {

    private Collection<? extends IResource<?>> dependencies;

    public Resource(IMakeSchema cacheSchema, Collection<? extends IResource<?>> dependencies) {
        super(cacheSchema);
        setDependencies(dependencies);
    }

    public Resource(IMakeSchema cacheSchema) {
        this(cacheSchema, Collections.<IResource<?>> emptyList());
    }

    public Resource(IMakeSchema cacheSchema, IResource<?>... dependencies) {
        this(cacheSchema, _wrap(dependencies));
    }

    private static Collection<? extends IResource<?>> _wrap(IResource<?>... dependencies) {
        return Arrays.asList(dependencies);
    }

    @Override
    public Collection<? extends IResource<?>> getDependencies() {
        return dependencies;
    }

    @Override
    public void setDependencies(Collection<? extends IResource<?>> dependencies) {
        if (dependencies == null)
            throw new NullPointerException("dependencies");
        this.dependencies = dependencies;
    }

}