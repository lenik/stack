package com.bee32.sem.makebiz.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.sem.makebiz.dto.DeliveryNoteDto;
import com.bee32.sem.makebiz.entity.DeliveryNote;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseDeliveryNoteDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseDeliveryNoteDialogBean.class);

    Boolean haveNoCorrespondingTicket = false;

    public ChooseDeliveryNoteDialogBean() {
        super(DeliveryNote.class, DeliveryNoteDto.class, 0);
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
