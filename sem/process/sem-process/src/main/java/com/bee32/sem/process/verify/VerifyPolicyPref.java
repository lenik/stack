package com.bee32.sem.process.verify;

import javax.free.IllegalUsageException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class VerifyPolicyPref
        extends EntityBean<String> {

    private static final long serialVersionUID = 1L;

    private String entityType;
    private VerifyPolicy<?> policy;

    @Column(length = 80, nullable = false)
    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    @Transient
    public Class<? extends EntityBean<?>> getEntityClass() {
        if (entityType == null)
            return null;

        Class<? extends EntityBean<?>> entityClass;
        try {
            entityClass = (Class<? extends EntityBean<?>>) Class.forName(entityType);
        } catch (ClassNotFoundException e) {
            throw new IllegalUsageException("Undefined entity type " + entityType);
        }

        if (!EntityBean.class.isAssignableFrom(entityClass))
            throw new IllegalUsageException("Invalid entity type: " + entityType);

        return entityClass;
    }

    public void setEntityClass(Class<? extends EntityBean<?>> entityClass) {
        if (entityClass == null)
            entityType = null;
        else
            entityType = entityClass.getName();
    }

    @ManyToOne
    public VerifyPolicy<?> getPolicy() {
        return policy;
    }

    public void setPolicy(VerifyPolicy<?> policy) {
        this.policy = policy;
    }

}
