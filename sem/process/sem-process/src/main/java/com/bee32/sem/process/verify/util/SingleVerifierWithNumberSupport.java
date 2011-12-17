package com.bee32.sem.process.verify.util;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.dto.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.sem.process.verify.builtin.IJudgeNumber;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;

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

class JudgeNumberInProperty
        implements IJudgeNumber {

    final Entity<?> entity;
    final IPropertyAccessor<? extends Number> property;
    final String description;

    public JudgeNumberInProperty(Entity<?> entity, IPropertyAccessor<? extends Number> property, String description) {
        if (entity == null)
            throw new NullPointerException("entity");
        if (description == null)
            throw new NullPointerException("description");
        this.entity = entity;
        this.property = property;
        this.description = description;
    }

    @Override
    public String getNumberDescription() {
        return description;
    }

    @Override
    public Number getJudgeNumber() {
        return property.get(entity);
    }

}
