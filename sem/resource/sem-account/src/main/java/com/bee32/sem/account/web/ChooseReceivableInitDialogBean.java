package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.ReceivableInitDto;
import com.bee32.sem.account.entity.ReceivableInit;

public class ChooseReceivableInitDialogBean
        extends ChooseReceivableDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseReceivableInitDialogBean() {
        super(ReceivableInit.class, ReceivableInitDto.class, 0, new Equals("class", "RINIT"));
    }

}
