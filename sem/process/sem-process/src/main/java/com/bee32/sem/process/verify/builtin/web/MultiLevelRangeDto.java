package com.bee32.sem.process.verify.builtin.web;

import java.util.List;

import javax.free.NotImplementedException;

import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.builtin.MultiLevelRange;

public class MultiLevelRangeDto
        extends EntityDto<MultiLevelRange, Integer> {

    private static final long serialVersionUID = 1L;

    private long limit;
    private int targetPolicyId;
    private String targetPolicyName;

    public MultiLevelRangeDto() {
        super();
    }

    public MultiLevelRangeDto(MultiLevelRange source) {
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
    protected void _marshal(MultiLevelRange source) {
        limit = source.getLimit();
        targetPolicyId = source.getTargetPolicy().getId();
        targetPolicyName = source.getTargetPolicy().getName();
    }

    @Override
    protected void _unmarshalTo(MultiLevelRange target) {
        target.setLimit(limit);
        // target.setTargetPolicy(verifyPolicy)
        throw new NotImplementedException();
    }

    public static List<MultiLevelRangeDto> marshalList(Iterable<? extends MultiLevelRange> entities) {
        return marshalList(MultiLevelRangeDto.class, entities);
    }

}
