package com.bee32.sem.process.verify.builtin.dto;

import com.bee32.plover.orm.ext.color.GreenEntityDto;
import com.bee32.sem.process.verify.VerifyPolicy;

public abstract class AbstractVerifyPolicyDto<E extends VerifyPolicy<?>>
        extends GreenEntityDto<E, Integer> {

    private static final long serialVersionUID = 1L;

    public AbstractVerifyPolicyDto() {
        super();
    }

    public AbstractVerifyPolicyDto(E source) {
        super(source);
    }

    public AbstractVerifyPolicyDto(int selection) {
        super(selection);
    }

    public AbstractVerifyPolicyDto(int selection, E source) {
        super(selection, source);
    }

}
