package com.bee32.sem.process.verify.builtin;

import javax.free.IllegalUsageException;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.orm.entity.Entity;

@MappedSuperclass
@Embeddable
public class SingleVerifierWithNumberSupport
        extends SingleVerifierSupport
        implements ISingleVerifierWithNumber {

    private static final long serialVersionUID = 1L;

    transient IJudgeNumber judgeNumberImpl;

    /**
     * @see #SingleVerifierWithNumberSupport(Entity, IPropertyAccessor, String)
     */
    // @Deprecated
    public SingleVerifierWithNumberSupport() {
    }

    public <E extends Entity<?>> SingleVerifierWithNumberSupport(E entity) {
        bind(entity);
    }

    public <E extends Entity<?>> SingleVerifierWithNumberSupport(E entity,
            IPropertyAccessor<? extends Number> property, String description) {
        bind(entity, property, description);
    }

    public void bind(Entity<?> entity) {
        if (!(entity instanceof IJudgeNumber))
            throw new IllegalUsageException(getClass() + " must be bound to " + IJudgeNumber.class);
        this.judgeNumberImpl = (IJudgeNumber) entity;
    }

    public <E extends Entity<?>> void bind(E entity, IPropertyAccessor<? extends Number> property, String description) {
        judgeNumberImpl = new JudgeNumberInProperty(entity, property, description);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof SingleVerifierWithNumberSupport)
            _populate((SingleVerifierWithNumberSupport) source);
        else
            super.populate(source);
    }

    protected void _populate(SingleVerifierWithNumberSupport o) {
        super._populate(o);
        judgeNumberImpl = o.judgeNumberImpl;
    }

    @Transient
    @Override
    public String getNumberDescription() {
        if (judgeNumberImpl == null)
            // throw new IllegalStateException("Verify context isn't bound yet");
            return "XXX Number-Description";
        return judgeNumberImpl.getNumberDescription();
    }

    @Transient
    @Override
    public Number getJudgeNumber() {
        if (judgeNumberImpl == null)
            // throw new IllegalStateException("Verify context isn't bound yet");
            return null;
        return judgeNumberImpl.getJudgeNumber();
    }

}
