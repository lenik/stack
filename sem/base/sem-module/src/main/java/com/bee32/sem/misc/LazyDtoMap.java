package com.bee32.sem.misc;

import java.io.Serializable;
import java.util.LinkedHashMap;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;

public class LazyDtoMap<D extends EntityDto<? extends Entity<?>, K>, K extends Serializable>
        extends LinkedHashMap<K, D> {

    private static final long serialVersionUID = 1L;

    transient CommonDataManager dataManager;

    final Class<Entity<?>> entityClass;
    final Class<D> dtoClass;
    final int fmask;

    public LazyDtoMap(Class<Entity<?>> entityClass, Class<D> dtoClass, int fmask) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.fmask = fmask;
    }

    public CommonDataManager getDataManager() {
        return dataManager;
    }

    public void setDataManager(CommonDataManager dataManager) {
        this.dataManager = dataManager;
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

    @SuppressWarnings({ "rawtypes" })
    protected D load(Serializable key) {
        Entity entity = dataManager.asFor(entityClass).get(key);
        D data = DTOs.mref((Class) dtoClass, fmask, entity);
        return data;
    }

}
