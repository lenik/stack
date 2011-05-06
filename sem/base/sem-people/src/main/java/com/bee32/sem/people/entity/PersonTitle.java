package com.bee32.sem.people.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.entity.EntityBean;

/**
 * 某人在某个公司中的具体职务
 */
@Entity
public class PersonTitle
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    Person person;
    Organization org;
    String orgUnit;
    String job;
    String role;
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
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        if (org == null)
            throw new NullPointerException("org");
        this.org = org;
    }

    /**
     * 所在部门
     */
    @Column(length = 30)
    public String getOrgUnit() {
        return orgUnit;
    }

    /**
     * 所在部门
     */
    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * 负责业务
     */
    // @Basic(optional = false)
    @Column(length = 50)
    public String getJob() {
        return job;
    }

    /**
     * 负责业务
     */
    public void setJob(String job) {
        this.job = job;
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

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
