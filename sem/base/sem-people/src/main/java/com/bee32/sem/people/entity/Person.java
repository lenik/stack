package com.bee32.sem.people.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.people.Gender;

@Entity
public class Person
        extends Party {

    private static final long serialVersionUID = 1L;

    Gender sex;

    String censusRegister;
    PersonSidType sidType;

    List<PersonContact> contacts;

    String interests;

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

    @OneToMany(mappedBy = "person")
    @Cascade(CascadeType.ALL)
    public List<PersonContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<PersonContact> contacts) {
        this.contacts = contacts;
    }

}
