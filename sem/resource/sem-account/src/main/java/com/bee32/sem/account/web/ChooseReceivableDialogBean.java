package com.bee32.sem.account.web;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.account.dto.ReceivableDto;
import com.bee32.sem.account.entity.Receivable;

public class ChooseReceivableDialogBean
        extends ChooseAccountAbleDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseReceivableDialogBean() {
        super(Receivable.class, ReceivableDto.class, 0, new Equals("class", "RABLE"));
    }

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> ChooseReceivableDialogBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

}
