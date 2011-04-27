package com.bee32.sem.process.verify.util;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyState;

@MappedSuperclass
public abstract class VerifiableEntityBean<K extends Serializable, C extends IVerifyContext>
        extends EntityBean<K>
        implements IVerifiable<C>, IVerifyContext {

    private static final long serialVersionUID = 1L;

    VerifyState verifyState = VerifyState.UNKNOWN;
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
    public VerifyState getVerifyState() {
        return verifyState;
    }

    void setVerifyState(VerifyState verifyState) {
        this.verifyState = verifyState;
    }

    @Transient
    @Column(nullable = false)
    public boolean isVerified() {
        return verifyState == VerifyState.VERIFIED;
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
