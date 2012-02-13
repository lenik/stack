package com.bee32.sem.process.base;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.IJudgeNumber;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

@MappedSuperclass
public abstract class ProcessEntity
        extends MomentInterval
        implements IVerifiable<ISingleVerifierWithNumber>, IJudgeNumber {

    private static final long serialVersionUID = 1L;

    SingleVerifierWithNumberSupport verifyContext;

    public ProcessEntity() {
        setVerifyContext(new SingleVerifierWithNumberSupport(this));
    }

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return verifyContext;
    }

    void setVerifyContext(SingleVerifierWithNumberSupport verifyContext) {
        if (verifyContext == null)
            throw new NullPointerException("verifyContext");
        this.verifyContext = verifyContext;
        this.verifyContext.bind(this);
    }

    @Transient
    @Override
    public String getNumberDescription() {
        return "amount";
    }

    @Transient
    @Override
    public final Number getJudgeNumber() { //XXX
        try {
            return computeJudgeNumber();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected Number computeJudgeNumber()
            throws Exception {
        return null;
    }

}
