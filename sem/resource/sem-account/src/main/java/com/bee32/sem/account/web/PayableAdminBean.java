package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.PayableDto;
import com.bee32.sem.account.entity.Payable;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class PayableAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public PayableAdminBean() {
        super(Payable.class, PayableDto.class, 0, new Equals("class", "PABLE"));
    }

}
