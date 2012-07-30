package com.bee32.sem.account.web;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.account.dto.PayableDto;
import com.bee32.sem.account.entity.Payable;

public class ChoosePayableDialogBean
        extends ChooseAccountAbleDialogBean {

    private static final long serialVersionUID = 1L;

    public ChoosePayableDialogBean() {
        super(Payable.class, PayableDto.class, 0, new Equals("class", "PABLE"));
    }

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> ChoosePayableDialogBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

}
