package com.bee32.sem.process.verify.builtin;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.dto.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;

@Embeddable
public class SingleVerifierWithNumberSupport
        extends SingleVerifierSupport
        implements ISingleVerifierWithNumber {

    private static final long serialVersionUID = 1L;

    private transient IJudgeNumber judgeNumberImpl;

    public <E extends Entity<?> & IJudgeNumber> SingleVerifierWithNumberSupport(E entity) {
        super(entity);
        this.judgeNumberImpl = entity;
    }

    public <E extends Entity<?>> SingleVerifierWithNumberSupport(E entity,
            IPropertyAccessor<? extends Number> property, String description) {
        super(entity);
        judgeNumberImpl = new JudgeNumberInProperty(entity, property, description);
    }

    @Transient
    @Override
    public String getNumberDescription() {
        return judgeNumberImpl.getNumberDescription();
    }

    @Transient
    @Override
    public Number getJudgeNumber() {
        return judgeNumberImpl.getJudgeNumber();
    }

}
