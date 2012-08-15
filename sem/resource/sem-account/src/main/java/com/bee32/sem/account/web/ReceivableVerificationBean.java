package com.bee32.sem.account.web;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.sem.account.dto.VerificationReceDto;
import com.bee32.sem.account.entity.VerificationRece;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ReceivableVerificationBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ReceivableVerificationBean() {
        super(VerificationRece.class, VerificationReceDto.class, 0, new Equals("class", "RECE"));
    }
}
