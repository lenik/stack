package com.bee32.sem.people.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Yellow;

/**
 * 某人在某个公司中的具体职务
 */
@Entity
@Yellow
public class PersonRole
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    Person person;
    Org org;
    OrgUnit orgUnit;
    String altOrgUnit;
    String role;
    String roleDetail;
    String description;

    @ManyToOne(optional = false)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if (person == null)
            throw new NullPointerException("person");
        this.person = person;
    }

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
     * 所在部门
     */
    @ManyToOne
    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    /**
     * 所在部门
     */
    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * （过渡）（临时使用的）所在部门名称
     */
    @Deprecated
    @Column(length = 30)
    public String getAltOrgUnit() {
        return altOrgUnit;
    }

    @Deprecated
    public void setAltOrgUnit(String altOrgUnit) {
        this.altOrgUnit = altOrgUnit;
    }

    /**
     * 职务
     */
    @Column(length = 30)
    public String getRole() {
        return role;
    }

    /**
     * 职务
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 负责业务
     */
    // @Basic(optional = false)
    @Column(length = 50)
    public String getRoleDetail() {
        return roleDetail;
    }

    /**
     * 负责业务
     */
    public void setRoleDetail(String roleDetail) {
        this.roleDetail = roleDetail;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
