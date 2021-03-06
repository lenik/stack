package com.bee32.sem.process.verify.preference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.ox1.typePref.TypePrefEntity;
import com.bee32.sem.process.verify.VerifyPolicy;

/**
 * 审核策略配置项
 *
 * <p lang="en">
 * Verify Policy Preference
 */
@Entity
public class VerifyPolicyPref
        extends TypePrefEntity {

    private static final long serialVersionUID = 1L;

    public static final int DESCRIPTION_LENGTH = 200;

    private VerifyPolicy preferredPolicy;
    private String description;

    @Override
    public void populate(Object source) {
        if (source instanceof VerifyPolicyPref)
            _populate((VerifyPolicyPref) source);
        else
            super.populate(source);
    }

    protected void _populate(VerifyPolicyPref o) {
        super._populate(o);
        preferredPolicy = o.preferredPolicy;
        description = o.description;
    }

    /**
     * 应用策略
     *
     * <p lang="en">
     */
    @ManyToOne
    public VerifyPolicy getPreferredPolicy() {
        return preferredPolicy;
    }

    public void setPreferredPolicy(VerifyPolicy preferredPolicy) {
        this.preferredPolicy = preferredPolicy;
    }

    /**
     * 描述
     *
     * <p lang="en">
     */
    @Column(length = DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
