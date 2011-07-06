package com.bee32.sem.people.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.icsf.principal.Group;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
@Green
public class Department
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    Org org;
    Group group;
    Contact contact;

    @ManyToOne(optional = false)
    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        if (org == null)
            throw new NullPointerException("org");
        this.org = org;
    }

    @ManyToOne
    public Group getGroup() {
        return group;
    }

    /**
     * @param group
     *            may be <code>null</code>.
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    @ManyToOne
    public Contact getContact() {
        return contact;
    }

    /**
     * @param contact
     *            may be <code>null</code>.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

}