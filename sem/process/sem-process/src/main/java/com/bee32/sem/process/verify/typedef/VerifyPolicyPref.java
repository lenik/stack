package com.bee32.sem.process.verify.typedef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.typePref.TypePrefEntity;
import com.bee32.plover.orm.util.Alias;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyPolicy;

@Entity
@Alias("pref")
public class VerifyPolicyPref
        extends TypePrefEntity {

    private static final long serialVersionUID = 1L;

    private VerifyPolicy<? extends IVerifyContext> preferredPolicy;
    private String description;

    @ManyToOne
    public VerifyPolicy<? extends IVerifyContext> getPreferredPolicy() {
        return preferredPolicy;
    }

    public void setPreferredPolicy(VerifyPolicy<? extends IVerifyContext> preferredPolicy) {
        this.preferredPolicy = preferredPolicy;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
