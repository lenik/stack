package com.bee32.sem.process.verify.builtin.web;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;

import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.builtin.Level;
import com.bee32.sem.process.verify.builtin.MultiLevel;

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
    protected void _unmarshalTo(IUnmarshalContext context, Level target) {

        target.setLimit(limit);

        WithContext<Level> with = with(context, target);
        with.unmarshal(multiLevelProperty, multiLevel);
        with.unmarshal(targetPolicyProperty, targetPolicy);
    }

    @Override
    protected void _parse(IVariantLookupMap<String> map)
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

    public Integer getTargetPolicyId() {
        return id(targetPolicy);
    }

    public String getTargetPolicyName() {
        if (targetPolicy == null)
            return null;
        else
            return targetPolicy.getName();
    }

    static final PropertyAccessor<Level, MultiLevel> multiLevelProperty = new PropertyAccessor<Level, MultiLevel>(
            MultiLevel.class) {

        @Override
        public MultiLevel get(Level entity) {
            return entity.getMultiLevel();
        }

        @Override
        public void set(Level entity, MultiLevel multiLevel) {
            entity.setMultiLevel(multiLevel);
        }

    };
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
