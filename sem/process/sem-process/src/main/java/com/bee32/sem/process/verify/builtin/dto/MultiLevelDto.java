package com.bee32.sem.process.verify.builtin.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.process.verify.builtin.MultiLevel;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class MultiLevelDto
        extends CEntityDto<MultiLevel, Integer> {

    private static final long serialVersionUID = 1L;

    private MultiLevelPolicyDto multiLevel;
    private long limit;
    private VerifyPolicyDto targetPolicy;

    public MultiLevelDto() {
        super();
    }

    @Override
    protected void _marshal(MultiLevel source) {
        multiLevel = new MultiLevelPolicyDto().ref(source.getMultiLevel());
        limit = source.getLimit();
        targetPolicy = mref(VerifyPolicyDto.class, source.getTargetPolicy());
    }

    @Override
    protected void _unmarshalTo(MultiLevel target) {
        target.setLimit(limit);

        merge(target, "multiLevel", multiLevel);
        merge(target, "targetPolicy", targetPolicy);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public MultiLevelPolicyDto getMultiLevel() {
        return multiLevel;
    }

    public void setMultiLevel(MultiLevelPolicyDto multiLevel) {
        this.multiLevel = multiLevel;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public VerifyPolicyDto getTargetPolicy() {
        return targetPolicy;
    }

    public void setTargetPolicy(VerifyPolicyDto targetPolicy) {
        this.targetPolicy = targetPolicy;
    }

}
