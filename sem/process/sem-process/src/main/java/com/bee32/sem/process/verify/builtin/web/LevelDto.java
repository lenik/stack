package com.bee32.sem.process.verify.builtin.web;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;

import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.Level;

public class LevelDto
        extends EntityDto<Level, Integer> {

    private static final long serialVersionUID = 1L;

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
        limit = source.getLimit();
        targetPolicy = new VerifyPolicyDto(source.getTargetPolicy());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, Level target) {
        target.setLimit(limit);

        with(context, target) //
                .unmarshal(targetPolicyProperty, targetPolicy);
    }

    @Override
    protected void _parse(IVariantLookupMap<String> map)
            throws ParseException {
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

    public Integer getTargetPolicyId() {
        return id(targetPolicy);
    }

    public String getTargetPolicyName() {
        if (targetPolicy == null)
            return null;
        else
            return targetPolicy.getName();
    }

    static final PropertyAccessor<Level, VerifyPolicy<?>> targetPolicyProperty = new PropertyAccessor<Level, VerifyPolicy<?>>(
            VerifyPolicy.class) {

        @Override
        public VerifyPolicy<?> get(Level entity) {
            return entity.getTargetPolicy();
        }

        @Override
        public void set(Level entity, VerifyPolicy<?> targetPolicy) {
            entity.setTargetPolicy(targetPolicy);
        }

    };

}
