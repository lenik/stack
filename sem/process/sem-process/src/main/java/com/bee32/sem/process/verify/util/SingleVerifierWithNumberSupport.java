package com.bee32.sem.process.verify.util;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

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
