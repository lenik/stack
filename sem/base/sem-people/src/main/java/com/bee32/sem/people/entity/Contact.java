package com.bee32.sem.people.entity;

import java.util.List;

import javax.free.Person;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.xp.EntityExt;

/**
 * 联系人。为“客户联系人”和“供应商联系人”的基类， 因为有共同的地方，所以提取本类
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Contact
        extends EntityExt<Long, ContactXP> {

    private static final long serialVersionUID = 1L;

    Person person;
    ContactCategory category;

    String email;
    String website;
    String qq;
    String workTel;
    String homeTel;
    String mobileTel;
    String fax;

    String address;
    String postCode;

    @Override
    protected List<ContactXP> getXPool() {
        return pool();
    }

    @Override
    protected void setXPool(List<ContactXP> xPool) {
        pool(xPool);
    }

    @ManyToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @ManyToOne
    public ContactCategory getCategory() {
        return category;
    }

    public void setCategory(ContactCategory category) {
        this.category = category;
    }

    @Column(length = 40)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(length = 80)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(length = 15)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Column(length = 30)
    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel;
    }

    @Column(length = 30)
    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    @Column(length = 20)
    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    @Column(length = 30)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(length = 10)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

}
