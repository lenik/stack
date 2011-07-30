package com.bee32.plover.orm.dao;

import java.io.Serializable;

import com.bee32.plover.inject.NotAComponent;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;

@NotAComponent
public class MemdbDataManager
        extends CommonDataManager {

    @Override
    public <E extends Entity<? extends K>, K extends Serializable> IEntityAccessService<E, K> asFor(
            Class<? extends E> entityType) {

        @SuppressWarnings("unchecked")
        Class<E> et = (Class<E>) entityType;

        MemTable tab = MemTable.getInstance(et);

        @SuppressWarnings("unchecked")
        IEntityAccessService<E, K> eas = (IEntityAccessService<E, K>) tab;

        return eas;
    }

    static final MemdbDataManager instance = new MemdbDataManager();

    public static MemdbDataManager getInstance() {
        return instance;
    }

}
