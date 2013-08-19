package com.bee32.sem.people.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.icsf.principal.Group;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

/**
 * 组织部门
 *
 * 企业组织中的单位部门。
 *
 * <p lang="en">
 * Organization Unit
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "org_unit_seq", allocationSize = 1)
public class OrgUnit
        extends TreeEntityAuto<Integer, OrgUnit> {

    private static final long serialVersionUID = 1L;

    Org org;
    Contact contact = new Contact();
    Group forWhichGroup;
    List<PersonRole> roles = new ArrayList<PersonRole>();

    @Override
    public void populate(Object source) {
        if (source instanceof OrgUnit)
            _populate((OrgUnit) source);
        else
            super.populate(source);
    }

    protected void _populate(OrgUnit o) {
        super._populate(o);
        org = o.org;
        contact = (Contact) o.contact.clone();
        forWhichGroup = o.forWhichGroup;
        roles = CopyUtils.copyList(o.roles);
    }

    @Override
    public void retarget(Object o) {
        super.retarget(o);
        _retarget((OrgUnit) o);
    }

    void _retarget(OrgUnit o) {
        _retargetMerge(roles, o.roles);
    }

    /**
     * 组织/公司
     *
     * 部门所在的顶层组织或公司。
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

    /**
     * 联系方式
     *
     * 部门的联系方式（可选）。
     *
     * 注：为简化，这里 contact 为必选。
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        if (contact == null)
            contact = new Contact();
        this.contact = contact;
    }

    /**
     * 用户组
     *
     * 组织部门对应的登录用户组。一个用户组最多只能对应一个组织部门。
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

    /**
     * 相关人员
     *
     * 部门中的相关人员和对应的职务列表。
     */
    @OneToMany(mappedBy = "orgUnit", cascade = CascadeType.ALL)
    public List<PersonRole> getRoles() {
        return roles;
    }

    public void setRoles(List<PersonRole> roles) {
        if (roles == null)
            throw new NullPointerException("roles");
        this.roles = roles;
    }

}
