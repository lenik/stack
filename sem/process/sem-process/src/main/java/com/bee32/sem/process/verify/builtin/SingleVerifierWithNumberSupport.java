package com.bee32.sem.process.verify.builtin;

import javax.free.IllegalUsageException;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.dto.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;

@MappedSuperclass
@Embeddable
public class SingleVerifierWithNumberSupport
        extends SingleVerifierSupport
        implements ISingleVerifierWithNumber {

    private static final long serialVersionUID = 1L;

    // private transient
    private transient IJudgeNumber judgeNumberImpl;

    public SingleVerifierWithNumberSupport() {
        super();
    }

    public SingleVerifierWithNumberSupport(Entity<?> entity) {
        super(entity);
    }

    public <E extends Entity<?>> SingleVerifierWithNumberSupport(E entity,
            IPropertyAccessor<? extends Number> property, String description) {
        super();
        bind(entity, property, description);
    }

    @Override
    public void bind(Entity<?> entity) {
        super.bind(entity);
        if (!(entity instanceof IJudgeNumber))
            throw new IllegalUsageException(getClass() + " must be bound to " + IJudgeNumber.class);
        this.judgeNumberImpl = (IJudgeNumber) entity;
    }

    public <E extends Entity<?>> void bind(E entity, IPropertyAccessor<? extends Number> property, String description) {
        super.bind(entity);
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
