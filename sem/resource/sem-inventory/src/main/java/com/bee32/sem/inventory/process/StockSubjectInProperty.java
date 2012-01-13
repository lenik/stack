package com.bee32.sem.inventory.process;

import java.io.Serializable;

import com.bee32.plover.arch.util.dto.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.sem.inventory.entity.StockOrderSubject;

public class StockSubjectInProperty
        implements Serializable,IStockSubjectAware {

    private static final long serialVersionUID = 1L;

    final Entity<?> entity;
    final IPropertyAccessor<StockOrderSubject> property;

    public StockSubjectInProperty(Entity<?> entity, IPropertyAccessor<StockOrderSubject> property) {
        if (entity == null)
            throw new NullPointerException("entity");
        if (property == null)
            throw new NullPointerException("property");
        this.entity = entity;
        this.property = property;
    }

    @Override
    public StockOrderSubject getSubject() {
        return property.get(entity);
    }

}
