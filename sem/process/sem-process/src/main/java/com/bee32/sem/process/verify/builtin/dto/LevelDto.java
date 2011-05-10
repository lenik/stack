package com.bee32.sem.process.verify.builtin.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.process.verify.builtin.Level;

public class LevelDto
        extends EntityDto<Level, Integer> {

    private static final long serialVersionUID = 1L;

    private MultiLevelDto multiLevel;
    private long limit;
    private VerifyPolicyDto targetPolicy;

    public LevelDto() {
        super();
    }

    public LevelDto(Level source) {
        super(source);
    }

    @Override
    protected void _marshal(Level source) {
        multiLevel = new MultiLevelDto().ref(source.getMultiLevel());
        limit = source.getLimit();
        targetPolicy = new VerifyPolicyDto(source.getTargetPolicy());
    }

    @Override
    protected void _unmarshalTo(Level target) {
        target.setLimit(limit);

        merge(target, "multiLevel", multiLevel);
        merge(target, "targetPolicy", targetPolicy);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public MultiLevelDto getMultiLevel() {
        return multiLevel;
    }

    public void setMultiLevel(MultiLevelDto multiLevel) {
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
