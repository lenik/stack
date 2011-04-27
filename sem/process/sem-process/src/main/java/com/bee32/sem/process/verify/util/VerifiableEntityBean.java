package com.bee32.sem.process.verify.util;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyState;

@MappedSuperclass
public abstract class VerifiableEntityBean<K extends Serializable, C extends IVerifyContext>
        extends EntityBean<K>
        implements IVerifiable<C>, IVerifyContext {

    private static final long serialVersionUID = 1L;

    int verifyStateId;
    String verifyError;
    Task verifyTask;

    public VerifiableEntityBean() {
        super();
    }

    public VerifiableEntityBean(String name) {
        super(name);
    }

    @Transient
    @SuppressWarnings("unchecked")
    @Override
    public C getVerifyContext() {
        return (C) this;
    }

    @Column(nullable = false)
    int getVerifyStateId() {
        return verifyStateId;
    }

    void setVerifyStateId(int stateId) {
        verifyStateId = stateId;
    }

    @Transient
    public EventState getVerifyState() {
        return EventState.get(verifyStateId);
    }

    void setVerifyState(EventState verifyState) {
        if (verifyState == null)
            throw new NullPointerException("verifyState");
        this.verifyStateId = verifyState.getId();
    }

    @Transient
    public abstract Date getVerifyUpdatedDate();

    @Transient
    @Column(nullable = false)
    public boolean isVerified() {
        return verifyStateId == VerifyState.VERIFIED.getId();
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

}
