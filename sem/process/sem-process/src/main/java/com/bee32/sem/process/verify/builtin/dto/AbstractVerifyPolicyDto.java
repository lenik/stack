package com.bee32.sem.process.verify.builtin.dto;

import com.bee32.plover.orm.ext.color.UIEntityDto;
import com.bee32.sem.process.verify.VerifyPolicy;

public abstract class AbstractVerifyPolicyDto<E extends VerifyPolicy<?>>
        extends UIEntityDto<E, Integer> {

    private static final long serialVersionUID = 1L;

    public AbstractVerifyPolicyDto() {
        super();
    }

    public AbstractVerifyPolicyDto(int selection) {
        super(selection);
    }

}
