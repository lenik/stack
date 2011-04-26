package com.bee32.sem.process.verify.util;

import java.io.Serializable;

import javax.persistence.Transient;

import com.bee32.sem.process.verify.IVerifiable2;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyException;

public class Verifiable2EntityBean<K extends Serializable, C extends IVerifyContext>
        extends VerifiableEntityBean<K, C>
        implements IVerifiable2<C> {

    private static final long serialVersionUID = 1L;

    private IVerifyPolicy<C> verifyPolicy;

    public Verifiable2EntityBean() {
        super();
    }

    public Verifiable2EntityBean(String name) {
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
