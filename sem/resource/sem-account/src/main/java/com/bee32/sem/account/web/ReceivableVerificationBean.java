package com.bee32.sem.account.web;

import com.bee32.sem.account.dto.VerificationDto;
import com.bee32.sem.account.entity.Verification;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ReceivableVerificationBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ReceivableVerificationBean() {
        super(Verification.class, VerificationDto.class, 0);
    }

}
