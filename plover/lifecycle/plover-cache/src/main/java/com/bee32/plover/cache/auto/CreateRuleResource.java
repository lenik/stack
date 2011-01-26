package com.bee32.plover.cache.auto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bee32.plover.cache.CacheCheckException;
import com.bee32.plover.cache.CacheRetrieveException;

public abstract class CreateRuleResource<T>
        extends Resource<T> {

    private Set<IResource<?>> dependencies;

    public CreateRuleResource(IMakeSchema cacheSchema) {
        super(cacheSchema);
        this.dependencies = new HashSet<IResource<?>>();
    }

    public CreateRuleResource(IMakeSchema cacheSchema, Collection<? extends IResource<?>> dependencies) {
        super(cacheSchema);
        setDependencies(dependencies);
    }

    public CreateRuleResource(IMakeSchema cacheSchema, IResource<?>... dependencies) {
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
        this.dependencies = new HashSet<IResource<?>>(dependencies);
    }

    public void addDependency(IResource<?> dependency) {
        this.dependencies.add(dependency);
    }

    public void removeDependency(IResource<?> dependency) {
        this.dependencies.remove(dependency);
    }

    public synchronized T pull()
            throws CacheRetrieveException {

        // Dereference at the first.
        T obj = cache.getCache();

        long myVersion = getVersion();

        List<Object> resolvedDeps = new ArrayList<Object>();
        long theirsVersion = resolveDependencies(resolvedDeps);

        boolean recreate = false;

        if (!cache.isCacheSet())
            recreate = true;
        else if (clock.compare(myVersion, theirsVersion) < 0)
            recreate = true;
        else if (schema.isForceToCreate(this))
            recreate = true;
        else {
            try {
                if (!checkValidity())
                    recreate = true;
            } catch (CacheCheckException e) {
                recreate = true;
            }
        }

        if (recreate) {
            try {
                obj = create(resolvedDeps);
            } catch (Exception e) {
                throw new CacheRetrieveException(e.getMessage(), e);
            }
            commit(obj);
        }

        return obj;
    }

    protected abstract T create(List<?> resolvedDependencies)
            throws Exception;

}
