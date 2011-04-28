package com.bee32.sem.process.verify.testbiz;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.util.AllowedBySupportDto;

public class AttackMissionDto
        extends AllowedBySupportDto<AttackMission, Integer> {

    private static final long serialVersionUID = 1L;

    private String target;

    public AttackMissionDto() {
        super();
    }

    public AttackMissionDto(AttackMission source) {
        super(source);
    }

    @Override
    protected void _marshal(AttackMission source) {
        target = source.getTarget();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, AttackMission target) {
        target.setTarget(this.target);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        target = map.getString("target");
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
