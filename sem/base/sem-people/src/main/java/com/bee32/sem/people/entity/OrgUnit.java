package com.bee32.sem.people.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.icsf.principal.Group;
import com.bee32.plover.orm.ext.tree.TreeEntityAuto;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "org_unit_seq", allocationSize = 1)
public class OrgUnit
        extends TreeEntityAuto<Integer, OrgUnit> {

    private static final long serialVersionUID = 1L;

    Org org;
    String name;
    Contact contact;
    Group forWhichGroup;

    /**
     * 属主组织。
     */
    // @NaturalId
    @ManyToOne(optional = false)
    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        if (org == null)
            throw new NullPointerException("org");
        this.org = org;
    }

    // @NaturalId
    @Column(length = 30, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name;
    }

    /**
     * 部门的联系方式(可选）
     */
    @ManyToOne(cascade = CascadeType.ALL)
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

    /**
     * 对应的用户组，一个用户组最多只能对应一个 OrgUnit。
     */
    @OneToOne(optional = true)
    public Group getForWhichGroup() {
        return forWhichGroup;
    }

    /**
     * @param forWhichGroup
     *            may be <code>null</code>.
     */
    public void setForWhichGroup(Group forWhichGroup) {
        this.forWhichGroup = forWhichGroup;
    }

}