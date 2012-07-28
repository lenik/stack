package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.PaidDto;
import com.bee32.sem.account.entity.Paid;

public class ChoosePaidDialogBean
        extends ChooseAccountAbleDialogBean {

    private static final long serialVersionUID = 1L;

    public ChoosePaidDialogBean() {
        super(Paid.class, PaidDto.class, 0, new Equals("class", "PED"));
    }

}
