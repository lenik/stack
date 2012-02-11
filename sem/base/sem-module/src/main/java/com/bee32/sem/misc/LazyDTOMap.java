package com.bee32.sem.misc;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;
import com.bee32.plover.orm.util.EntityDto;

public class LazyDTOMap<K extends Serializable, D extends EntityDto<? extends Entity<?>, K>>
        extends LinkedHashMap<K, D> {

    private static final long serialVersionUID = 1L;

    final Class<? extends Entity<?>> entityClass;
    final Class<D> dtoClass;
    final int fmask;

    public LazyDTOMap(Class<? extends Entity<?>> entityClass, Class<D> dtoClass, int fmask) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.fmask = fmask;
    }

    @SuppressWarnings("unchecked")
    @Override
    public D get(Object key) {
        D data = super.get(key);
        if (data == null) {
            synchronized (this) {
                if (data == null) {
                    data = load((Serializable) key);
                    super.put((K) key, data);
                }
            }
        }
        return data;
    }

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected D load(Serializable key) {
        Entity entity = ctx.data.access(entityClass).get(key);
        D data = (D) DTOs.mref((Class) dtoClass, fmask, entity);
        return data;
    }

}
