package com.bee32.sem.people.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("PER")
public class PersonContact
        extends Contact {

    private static final long serialVersionUID = 1L;

    String homeTel;
    String workTel;
    String mobileTel;
    String qq;
    String msn;

    @Transient
    public Person getPerson() {
        Party party = (Person) getParty();
        if (!(party instanceof Person))
            throw new IllegalStateException("Party of a person contact isn't a person.");
        return (Person) party;
    }

    public void setPerson(Person person) {
        if (person == null)
            throw new NullPointerException("person");
        setParty(person);
    }

    @Column(length = 30)
    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    @Column(length = 30)
    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel;
    }

    @Column(length = 20)
    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    @Column(length = 15)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Column(length = 40)
    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

}
