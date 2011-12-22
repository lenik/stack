package com.bee32.sem.process.verify.builtin.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.process.verify.builtin.SingleVerifierLevel;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class SingleVerifierLevelDto
        extends CEntityDto<SingleVerifierLevel, Integer> {

    private static final long serialVersionUID = 1L;

    private SingleVerifierRankedPolicyDto policy;
    private long limit;
    private VerifyPolicyDto targetPolicy;

    public SingleVerifierLevelDto() {
        super();
    }

    @Override
    protected void _marshal(SingleVerifierLevel source) {
        policy = mref(SingleVerifierRankedPolicyDto.class, source.getPolicy());
        limit = source.getLimit();
        targetPolicy = mref(VerifyPolicyDto.class, source.getTargetPolicy());
    }

    @Override
    protected void _unmarshalTo(SingleVerifierLevel target) {
        target.setLimit(limit);
        merge(target, "policy", policy);
        merge(target, "targetPolicy", targetPolicy);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public SingleVerifierRankedPolicyDto getPolicy() {
        return policy;
    }

    public void setPolicy(SingleVerifierRankedPolicyDto policy) {
        this.policy = policy;
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
