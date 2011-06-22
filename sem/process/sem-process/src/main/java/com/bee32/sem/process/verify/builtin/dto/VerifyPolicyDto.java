package com.bee32.sem.process.verify.builtin.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.process.verify.VerifyPolicy;

public final class VerifyPolicyDto
        extends AbstractVerifyPolicyDto<VerifyPolicy> {

    private static final long serialVersionUID = 1L;

    public VerifyPolicyDto() {
        super();
    }

    public VerifyPolicyDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(VerifyPolicy source) {
    }

    @Override
    protected void _unmarshalTo(VerifyPolicy target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
