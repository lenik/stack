package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.PayableInitDto;
import com.bee32.sem.account.entity.PayableInit;

public class ChoosePayableInitDialogBean
        extends ChoosePayableDialogBean {

    private static final long serialVersionUID = 1L;

    public ChoosePayableInitDialogBean() {
        super(PayableInit.class, PayableInitDto.class, 0, new Equals("class", "PINIT"));
    }

}
