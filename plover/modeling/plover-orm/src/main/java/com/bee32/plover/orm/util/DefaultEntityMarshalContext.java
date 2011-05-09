package com.bee32.plover.orm.util;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;

@Component
@Lazy
public class DefaultEntityMarshalContext
        implements IEntityMarshalContext {

    @Inject
    CommonDataManager dataManager;

    @Override
    public <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id) {
        E entity = dataManager.load(entityType, id);
        return entity;
    }

}
