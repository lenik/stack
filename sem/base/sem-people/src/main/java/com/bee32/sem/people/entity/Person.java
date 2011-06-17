package com.bee32.sem.people.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.people.Gender;

@Entity
@DiscriminatorValue("PER")
public class Person
        extends Party {

    private static final long serialVersionUID = 1L;

    Gender sex;

    String censusRegister;
    PersonSidType sidType;

    Set<PersonRole> roles = new HashSet<PersonRole>();

    /**
     * 性别
     */
    @Enumerated(EnumType.ORDINAL)
    public Gender getSex() {
        return sex;
    }

    /**
     * 性别
     */
    public void setSex(Gender sex) {
        this.sex = sex;
    }


    /**
     * 户籍
     */
    @Column(length = 15)
    public String getCensusRegister() {
        return censusRegister;
    }

    /**
     * 户籍
     */
    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    /**
     * 身份证件类型 (SID = Social ID)
     */
    @ManyToOne
    public PersonSidType getSidType() {
        return sidType;
    }

    /**
     * 身份证件类型 (SID = Social ID)
     */
    public void setSidType(PersonSidType sidType) {
        this.sidType = sidType;
    }

    @OneToMany(mappedBy = "person")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<PersonRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<PersonRole> roles) {
        if (roles == null)
            throw new NullPointerException("roles");
        this.roles = roles;
    }

}
