package com.bee32.sem.account.web;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.account.dto.AccountAbleDto;
import com.bee32.sem.account.entity.AccountAble;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseAccountAbleDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseAccountAbleDialogBean.class);

    public ChooseAccountAbleDialogBean() {
        this(AccountAble.class, AccountAbleDto.class, 0, new Equals("class", "ABLE"));
    }

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> ChooseAccountAbleDialogBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }
}
