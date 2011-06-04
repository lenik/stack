package com.bee32.sem.people.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("ORG")
public class OrgContact
        extends Contact {

    private static final long serialVersionUID = 1L;

    @Transient
    public Org getOrg() {
        Party party = getParty();
        if (!(party instanceof Org))
            throw new IllegalStateException("Party of an org contact isn't an org.");
        return (Org) party;
    }

    public void setOrg(Org org) {
        if (org == null)
            throw new NullPointerException("org");
        setParty(org);
    }

}
