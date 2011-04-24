package com.bee32.sem.process.verify.builtin.web;

import javax.free.NotImplementedException;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.builtin.Level;

public class LevelDto
        extends EntityDto<Level, Integer> {

    private static final long serialVersionUID = 1L;

    private long limit;
    private int targetPolicyId;
    private String targetPolicyName;

    public LevelDto() {
        super();
    }

    public LevelDto(Level source) {
        super(source);
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public int getTargetPolicyId() {
        return targetPolicyId;
    }

    public void setTargetPolicyId(int targetPolicyId) {
        this.targetPolicyId = targetPolicyId;
    }

    public String getTargetPolicyName() {
        return targetPolicyName;
    }

    public void setTargetPolicyName(String targetPolicyName) {
        this.targetPolicyName = targetPolicyName;
    }

    @Override
    protected void _marshal(Level source) {
        limit = source.getLimit();
        targetPolicyId = source.getTargetPolicy().getId();
        targetPolicyName = source.getTargetPolicy().getName();
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, Level target) {
        target.setLimit(limit);
        // target.setTargetPolicy(verifyPolicy)
        throw new NotImplementedException();
    }

}
