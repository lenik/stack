package com.bee32.sem.people.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.xp.EntityExt;

/**
 * 联系人。为“客户联系人”和“供应商联系人”的基类， 因为有共同的地方，所以提取本类
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 4)
@DiscriminatorValue("BASE")
public class Contact
        extends EntityExt<Long, ContactXP> {

    private static final long serialVersionUID = 1L;

    ContactCategory category;
    String address;
    String postCode;
    String tel;
    String fax;
    String email;
    String website;

    @Override
    protected List<ContactXP> getXPool() {
        return pool();
    }

    @Override
    protected void setXPool(List<ContactXP> xPool) {
        pool(xPool);
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
