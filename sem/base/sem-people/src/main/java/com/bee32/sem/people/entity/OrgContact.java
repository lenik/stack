package com.bee32.sem.people.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("ORG")
public class OrgContact
        extends Contact {

    private static final long serialVersionUID = 1L;

    Org org;

    @ManyToOne(optional = false)
    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        if (org == null)
            throw new NullPointerException("org");
        this.org = org;
    }

}
