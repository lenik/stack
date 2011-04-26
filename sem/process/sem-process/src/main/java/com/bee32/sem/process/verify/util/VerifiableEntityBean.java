package com.bee32.sem.process.verify.util;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;

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
    @SuppressWarnings("unchecked")
    @Override
    public C getVerifyContext() {
        return (C) this;
    }

}
