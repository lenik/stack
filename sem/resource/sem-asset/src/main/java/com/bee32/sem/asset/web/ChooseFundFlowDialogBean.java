package com.bee32.sem.asset.web;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.asset.dto.CreditNoteDto;
import com.bee32.sem.asset.entity.CreditNote;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseFundFlowDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseFundFlowDialogBean.class);

    Boolean haveNoCorrespondingTicket = false;

    public ChooseFundFlowDialogBean() {
        super(CreditNote.class, CreditNoteDto.class, 0);
    }

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> ChooseFundFlowDialogBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (haveNoCorrespondingTicket) {
            elements.add(new IsNull("ticket"));
        }
    }

    public Boolean getHaveNoCorrespondingTicket() {
        return haveNoCorrespondingTicket;
    }

    public void setHaveNoCorrespondingTicket(Boolean haveNoCorrespondingTicket) {
        this.haveNoCorrespondingTicket = haveNoCorrespondingTicket;
    }


}
