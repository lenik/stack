package com.bee32.sem.people.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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

    String interests;

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
     * 兴趣爱好
     */
    @Column(length = 100)
    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
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

    @Transient
    @SuppressWarnings("unchecked")
    public List<PersonContact> getContacts() {
        List<? extends Contact> _contacts = super.getContacts_();
        return (List<PersonContact>) _contacts;
    }

    public void setContacts(List<PersonContact> contacts) {
        if (contacts == null)
            throw new NullPointerException("contacts");
        super.setContacts_(contacts);
    }

    @OneToMany
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
