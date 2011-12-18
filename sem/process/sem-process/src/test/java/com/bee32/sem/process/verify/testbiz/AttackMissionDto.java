package com.bee32.sem.process.verify.testbiz;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class AttackMissionDto
        extends UIEntityDto<AttackMission, Integer>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    private String target;
    private SingleVerifierSupportDto verifyContext;

    @Override
    protected void _marshal(AttackMission source) {
        target = source.getTarget();
        verifyContext = marshal(SingleVerifierSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(AttackMission target) {
        target.setTarget(this.target);
        merge(target, "verifyContext", verifyContext);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        target = map.getString("target");
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public SingleVerifierSupportDto getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierSupportDto verifyContext) {
        this.verifyContext = verifyContext;
    }

}
