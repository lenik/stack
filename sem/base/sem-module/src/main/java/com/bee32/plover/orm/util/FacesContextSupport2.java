package com.bee32.plover.orm.util;

import java.io.Serializable;

import com.bee32.plover.faces.utils.FacesContextSupport;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;

public abstract class FacesContextSupport2
        extends FacesContextSupport {

    protected static CommonDataManager getDataManager() {
        CommonDataManager dataManager = getBean(CommonDataManager.class);
        return dataManager;
    }

    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> serviceFor(Class<E> entityType) {
        IEntityAccessService<E, K> service = getDataManager().asFor(entityType);
        return service;
    }

}
