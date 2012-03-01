package com.bee32.sem.process.verify.builtin;

import java.io.Serializable;

import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;

public class JudgeNumberInProperty
        implements IJudgeNumber {

    final Entity<?> entity;
    final IPropertyAccessor<? extends Number> property;
    final String description;

    public JudgeNumberInProperty(Entity<?> entity, IPropertyAccessor<? extends Number> property, String description) {
        if (entity == null)
            throw new NullPointerException("entity");
        if (description == null)
            throw new NullPointerException("description");
        this.entity = entity;
        this.property = property;
        this.description = description;
    }

    @Override
    public String getNumberDescription() {
        return description;
    }

    @Override
    public Number getJudgeNumber() {
        return property.get(entity);
    }

}
