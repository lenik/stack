package com.bee32.plover.cache.pull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.free.CreateException;
import javax.free.ObjectInfo;
import javax.free.Pred0;

import com.bee32.plover.cache.CacheCheckException;
import com.bee32.plover.cache.CacheRetrieveException;

public abstract class Resource<T>
        extends Pred0 {

    private final ICacheSchema cacheSchema;
    private final Ref<T> ref;

    private long version;

    public Resource(ICacheSchema cacheSchema, Ref<T> ref) {
        if (cacheSchema == null)
            throw new NullPointerException("cacheSchema");
        if (ref == null)
            throw new NullPointerException("ref");
        this.cacheSchema = cacheSchema;
        this.ref = ref;
        this.version = -1;
    }

    public Resource(ICacheSchema cacheSchema) {
        this(cacheSchema, new NullableRef<T>());
    }

    public String getName() {
        return ObjectInfo.getObjectId(this);
    }

    public String getDescription() {
        return getName();
    }

    public long getVersion() {
        return version;
    }

    @Override
    public final boolean test() {
        try {
            return checkState();
        } catch (CacheCheckException e) {
            return false;
        }
    }

    public boolean checkState()
            throws CacheCheckException {
        return checkStateOfDependencies();
    }

    protected final boolean checkStateOfDependencies()
            throws CacheCheckException {
        for (Resource<?> dep : getDependencies()) {
            if (!dep.checkState())
                return false;
        }
        return true;
    }

    public Collection<Resource<?>> getDependencies() {
        return Collections.emptyList();
    }

    public synchronized T pull()
            throws CacheRetrieveException {
        boolean cacheValidity = test();

        T obj;

        if (ref.isSet()) {
            obj = ref.get();

            List<Object> resolvedDeps = new ArrayList<Object>();
            long maxVersion = resolveDependencies(resolvedDeps);

            obj = create(resolvedDeps);
            ref.set(obj);

            // The cache contents now have been generated.
            ref.set(obj);
            version = cacheSchema.getClock().tick();
        }
        return obj;
    }

    protected long resolveDependencies(List<Object> resolvedDeps)
            throws CreateException {
        long maxVersion = this.version;

        for (Resource<?> dep : getDependencies()) {
            long depVersion = dep.getVersion();

            if (maxVersion < depVersion)
                maxVersion = depVersion;

            Object depObj = dep.pull();

            if (resolvedDeps != null)
                resolvedDeps.add(depObj);
        }

        return maxVersion;
    }

    protected abstract T create(List<?> resolvedDependencies)
            throws CreateException;

}
