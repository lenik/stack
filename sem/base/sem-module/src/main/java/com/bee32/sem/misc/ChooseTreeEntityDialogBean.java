package com.bee32.sem.misc;

import java.io.Serializable;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public abstract class ChooseTreeEntityDialogBean
        extends TreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    String header;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> ChooseTreeEntityDialogBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

    public String getObjectTypeName() {
        String entityName = ClassUtil.getTypeName(entityClass);
        return entityName;
    }

    public final String getHeader() {
        if (header != null)
            return header;
        return "请选择一个" + getObjectTypeName() + "…";
    }

    public final void setHeader(String header) {
        this.header = header;
    }

}
