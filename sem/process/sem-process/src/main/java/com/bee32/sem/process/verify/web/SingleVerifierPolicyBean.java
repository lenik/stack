package com.bee32.sem.process.verify.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierPolicyDto;

@ForEntity(SingleVerifierPolicy.class)
public class SingleVerifierPolicyBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public SingleVerifierPolicyBean() {
        super(SingleVerifierPolicy.class, SingleVerifierPolicyDto.class, 0);
    }

}
