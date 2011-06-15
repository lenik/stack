package com.bee32.plover.orm.util;

import java.io.Serializable;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.web.faces.view.ViewBean;

public abstract class EntityViewBean
        extends ViewBean
        implements IEntityMarshalContext {

    private static final long serialVersionUID = 1L;

    @Override
    public <E extends Entity<K>, K extends Serializable> E loadEntity(Class<E> entityType, K id) {
        CommonDataManager dataManager = getBean(CommonDataManager.class);
        E entity = dataManager.fetch(entityType, id);
        return entity;
    }

    protected CommonDataManager getDataManager() {
        CommonDataManager dataManager = getBean(CommonDataManager.class);
        return dataManager;
    }

}
