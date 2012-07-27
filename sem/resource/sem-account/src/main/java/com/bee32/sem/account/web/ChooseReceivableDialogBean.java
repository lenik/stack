package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.ReceivableDto;
import com.bee32.sem.account.entity.Receivable;

public class ChooseReceivableDialogBean
        extends ChooseAccountAbleDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseReceivableDialogBean() {
        super(Receivable.class, ReceivableDto.class, 0, new Equals("class", "RABLE"));
    }

}
