package com.bee32.sem.process.verify.util;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.ext.c.CEntityAuto;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyState;

@MappedSuperclass
public abstract class VerifiableEntity<K extends Serializable, C extends IVerifyContext>
        extends CEntityAuto<K>
        implements IVerifiable<C>, IVerifyContext {

    private static final long serialVersionUID = 1L;

    EventState verifyState = VerifyState.UNKNOWN;
    String verifyError;
    Date verifyEvalDate;
    Task verifyTask;

    public VerifiableEntity() {
        super();
    }

    public VerifiableEntity(String name) {
        super(name);
    }

    @Transient
    @SuppressWarnings("unchecked")
    @Override
    public C getVerifyContext() {
        return (C) this;
    }

    @Column(name = "verifyState", nullable = false)
    int getVerifyState_() {
        return verifyState.getValue();
    }

    void setVerifyState_(int stateValue) {
        verifyState = VerifyState.valueOf(stateValue);
    }

    @Transient
    public EventState getVerifyState() {
        return verifyState;
    }

    void setVerifyState(EventState verifyState) {
        if (verifyState == null)
            throw new NullPointerException("verifyState");
        this.verifyState = verifyState;
    }

    @Temporal(TIMESTAMP)
    public Date getVerifyEvalDate() {
        return verifyEvalDate;
    }

    void setVerifyEvalDate(Date verifyEvalDate) {
        this.verifyEvalDate = verifyEvalDate;
    }

    @Transient
    @Column(nullable = false)
    public boolean isVerified() {
        return VerifyState.VERIFIED.equals(verifyState);
    }

    @Column(length = 100)
    public String getVerifyError() {
        return verifyError;
    }

    void setVerifyError(String error) {
        this.verifyError = error;
    }

    @OneToOne
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Task getVerifyTask() {
        return verifyTask;
    }

    public void setVerifyTask(Task verifyTask) {
        this.verifyTask = verifyTask;
    }

    @Override
    protected boolean isLocked() {
        if (VerifyState.VERIFIED.equals(verifyState))
            return true;
        return super.isLocked();
    }

}
