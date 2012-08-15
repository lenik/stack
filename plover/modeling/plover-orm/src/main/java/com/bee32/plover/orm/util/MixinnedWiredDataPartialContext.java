package com.bee32.plover.orm.util;

import java.io.Serializable;

import com.bee32.plover.arch.util.BeanPartialContext;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;

public class MixinnedWiredDataPartialContext
        extends WiredDataPartialContext {

    public MixinnedWiredDataPartialContext(BeanPartialContext bean) {
        super(bean);
    }

    /**
     * {@inheritDoc}
     *
     * @deprecated This method is deprecated in mixin classes. Please use DATA(...) instead.
     */
    @Override
    public <E extends Entity<? extends K>, K extends Serializable> IEntityAccessService<E, K> access(
            Class<? extends E> entityType) {
        return super.access(entityType);
    }

}