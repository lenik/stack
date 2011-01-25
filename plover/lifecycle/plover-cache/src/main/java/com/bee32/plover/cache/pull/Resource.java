package com.bee32.plover.cache.pull;

import java.util.Collection;
import java.util.Collections;

import javax.free.CreateException;
import javax.free.ObjectInfo;
import javax.free.Pred0;

public abstract class Resource<T>
        extends Pred0 {

    private final ICacheSchema cacheSchema;
    private final Ref<T> ref;

    private int serialVersion;

    public Resource(ICacheSchema cacheSchema, Ref<T> ref) {
        if (cacheSchema == null)
            throw new NullPointerException("cacheSchema");
        if (ref == null)
            throw new NullPointerException("ref");
        this.cacheSchema = cacheSchema;
        this.ref = ref;
        this.serialVersion = -1;
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

    public int getSerialVersion() {
        return serialVersion;
    }

    @Override
    public final boolean test() {
        return checkValidity();
    }

    public boolean checkValidity() {
        return checkValidityOfDependencies();
    }

    boolean checkValidityOfDependencies() {
        for (Resource<?> dep : getDependencies()) {
            if (!dep.checkValidity())
                return false;
        }
        return true;
    }

    public Collection<Resource<?>> getDependencies() {
        return Collections.emptyList();
    }

    public synchronized T pull()
            throws CreateException {
        T obj;
        if (ref.isSet())
            obj = ref.get();
        else {
            obj = create();
            ref.set(obj);
            serialVersion = cacheSchema.tick();
        }
        return obj;
    }

    protected abstract T create()
            throws CreateException;

}
