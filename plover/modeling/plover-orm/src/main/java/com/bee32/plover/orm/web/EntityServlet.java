package com.bee32.plover.orm.web;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.util.DefaultDataAssembledContext;
import com.bee32.plover.servlet.central.PloverServlet;

public abstract class EntityServlet
        extends PloverServlet {

    private static final long serialVersionUID = 1L;

    protected static class ctx
            extends DefaultDataAssembledContext {
    }

    protected static <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> DATA(Class<? extends E> entityType) {
        return ctx.data.access(entityType);
    }

}
