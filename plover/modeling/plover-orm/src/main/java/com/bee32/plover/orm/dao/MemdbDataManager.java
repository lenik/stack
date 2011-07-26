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

        Memdb memdao = Memdb.getInstance(et);

        @SuppressWarnings("unchecked")
        IEntityAccessService<E, K> memEas = (IEntityAccessService<E, K>) memdao;

        return memEas;
    }

    public static final MemdbDataManager INSTANCE = new MemdbDataManager();

}
