package com.bee32.sem.process.base;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.model.ModelTemplate;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.IJudgeNumber;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

@ModelTemplate
@MappedSuperclass
public abstract class ProcessEntity
        extends MomentInterval
        implements IVerifiable<ISingleVerifierWithNumber>, IJudgeNumber {

    private static final long serialVersionUID = 1L;

    SingleVerifierWithNumberSupport verifyContext;

    @Override
    public void populate(Object source) {
        if (source instanceof ProcessEntity)
            _populate((ProcessEntity) source);
        else
            super.populate(source);
    }

    protected void _populate(ProcessEntity o) {
        super._populate(o);
        verifyContext = (SingleVerifierWithNumberSupport) o.verifyContext.clone();
    }

    @Override
    protected void createTransients() {
        if (verifyContext == null)
            verifyContext = new SingleVerifierWithNumberSupport(this);
        verifyContext.bind(this);
    }

    /**
     * 审核上下文
     */
    @Deprecated
    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return verifyContext;
    }

    @Deprecated
    protected void setVerifyContext(SingleVerifierWithNumberSupport verifyContext) {
        if (verifyContext == null)
            throw new NullPointerException("verifyContext");
        this.verifyContext = verifyContext;
        this.verifyContext.bind(this);
    }

    @Deprecated
    @Transient
    @Override
    public String getNumberDescription() {
        return "amount";
    }

    @Deprecated
    @Transient
    @Override
    public final Number getJudgeNumber() {
        try {
            return computeJudgeNumber();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Deprecated
    protected Number computeJudgeNumber()
            throws Exception {
        return null;
    }

}
