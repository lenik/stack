package com.bee32.plover.cache.auto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.free.ObjectInfo;
import javax.free.Pred0;

import com.bee32.plover.cache.CacheCheckException;
import com.bee32.plover.cache.CacheRetrieveException;
import com.bee32.plover.cache.ref.CacheRef;

/**
 * Base resource doesn't have any dependency.
 */
public abstract class BaseResource<T>
        extends Pred0
        implements IResource<T> {

    protected final IMakeSchema schema;
    protected final CacheRef<T> cache;
    protected final ITickClock clock;

    private long version;

    public BaseResource(IMakeSchema makeSchema) {
        if (makeSchema == null)
            throw new NullPointerException("makeSchema");
        this.schema = makeSchema;
        this.cache = makeSchema.getCacheAllocator().newCache();
        this.clock = makeSchema.getClock();
    }

    @Override
    public String getName() {
        return ObjectInfo.getObjectId(this);
    }

    @Override
    public String getDescription() {
        return getName();
    }

    @Override
    public Collection<? extends IResource<?>> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public void setDependencies(Collection<? extends IResource<?>> dependencies) {
// Just ignore.
    }

    protected long resolveDependencies(List<Object> resolvedDeps)
            throws CacheRetrieveException {
        long latestVersionOfDependencies = this.getVersion();

        for (IResource<?> dependency : getDependencies()) {
            long dependencyVersion = dependency.getVersion();

            if (clock.compare(latestVersionOfDependencies, dependencyVersion) < 0)
                latestVersionOfDependencies = dependencyVersion;

            Object resolved = dependency.pull();

            if (resolvedDeps != null)
                resolvedDeps.add(resolved);
        }

        return latestVersionOfDependencies;
    }

    @Override
    public boolean exists() {
        return cache.isCacheSet();
    }

    @Override
    public boolean checkValidity()
            throws CacheCheckException {
        return true;
    }

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public long getLastModifiedTime() {
        return version;
    }

    @Override
    public IMakeSchema getSchema() {
        return schema;
    }

    @Override
    public CacheRef<T> getCached() {
        return cache;
    }

    /**
     * Store the obj to the cache and update the version.
     */
    protected T commit(T obj) {
        CacheRef<T> cache = getCached();
        if (cache != null) {
            cache.setCache(obj);
            version = clock.tick();
        }
        return obj;
    }

    // Pred0

    @Override
    public final boolean test() {
        if (!exists())
            return false;
        try {
            if (!checkValidity())
                return false;
        } catch (CacheCheckException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getDescription();
    }

}
