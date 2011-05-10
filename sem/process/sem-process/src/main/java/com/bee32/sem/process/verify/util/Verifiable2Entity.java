package com.bee32.sem.process.verify.util;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.sem.process.verify.IVerifiable2;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyException;

@MappedSuperclass
@Deprecated
public abstract class Verifiable2Entity<K extends Serializable, C extends IVerifyContext>
        extends VerifiableEntity<K, C>
        implements IVerifiable2<C> {

    private static final long serialVersionUID = 1L;

    private IVerifyPolicy<C> verifyPolicy;

    public Verifiable2Entity() {
        super();
    }

    public Verifiable2Entity(String name) {
        super(name);
    }

    @Transient
    @Override
    public IVerifyPolicy<C> getVerifyPolicy() {
        return verifyPolicy;
    }

    void setVerifyPolicy(IVerifyPolicy<C> verifyPolicy) {
        this.verifyPolicy = verifyPolicy;
    }

    @Override
    public void verify()
            throws VerifyException {
        getVerifyPolicy().verify(getVerifyContext());
    }

    @Transient
    @Override
    public boolean isVerified() {
        return getVerifyPolicy().isVerified(getVerifyContext());
    }

}
