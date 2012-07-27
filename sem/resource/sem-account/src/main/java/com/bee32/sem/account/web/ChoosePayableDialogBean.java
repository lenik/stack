package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.PayableDto;
import com.bee32.sem.account.entity.Payable;

public class ChoosePayableDialogBean
        extends ChooseAccountAbleDialogBean {

    private static final long serialVersionUID = 1L;

    public ChoosePayableDialogBean() {
        super(Payable.class, PayableDto.class, 0, new Equals("class", "PABLE"));
    }

}
