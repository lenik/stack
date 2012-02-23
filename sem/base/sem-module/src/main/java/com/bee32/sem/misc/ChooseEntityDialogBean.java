package com.bee32.sem.misc;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public class ChooseEntityDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> ChooseEntityDialogBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

}
