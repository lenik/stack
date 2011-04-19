package com.bee32.sem.process.verify.util;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.IVerifyPolicy;
import com.bee32.sem.process.verify.VerifyException;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPrefs;

@MappedSuperclass
public abstract class VerifiableEntityBean<K extends Serializable, C extends IVerifyContext>
        extends EntityBean<K>
        implements IVerifiable<C>, IVerifyContext {

    private static final long serialVersionUID = 1L;

    public VerifiableEntityBean() {
        super();
    }

    public VerifiableEntityBean(String name) {
        super(name);
    }

    @Transient
    @Override
    public IVerifyPolicy<C> getVerifyPolicy() {
        Class<? extends IVerifiable<C>> cast = (Class<? extends IVerifiable<C>>) getClass();
        return VerifyPolicyPrefs.forEntityType(cast);
    }

    @Transient
    @SuppressWarnings("unchecked")
    @Override
    public C getVerifyContext() {
        return (C) this;
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
