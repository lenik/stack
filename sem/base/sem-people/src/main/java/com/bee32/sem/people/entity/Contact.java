package com.bee32.sem.people.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.plover.orm.ext.xp.EntityExt;

/**
 * 联系人。为“客户联系人”和“供应商联系人”的基类， 因为有共同的地方，所以提取本类
 */
@Entity
@Blue
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// @DiscriminatorColumn(name = "stereo", length = 4)
// @DiscriminatorValue("-")
public class Contact
        extends EntityExt<Integer, ContactXP> {

    private static final long serialVersionUID = 1L;

    Party party;
    ContactCategory category = ContactCategory.NORMAL;

    String address;
    String postCode;
    String tel;
    String mobile;
    String fax;
    String email;
    String website;
    String qq;

    public Contact() {
    }

    public Contact(Party party, ContactCategory category) {
        if (party == null)
            throw new NullPointerException("party");
        if (category == null)
            throw new NullPointerException("category");
        this.party = party;
        this.category = category;
    }

    /**
     * 联系人分类
     */
    @ManyToOne
    public ContactCategory getCategory() {
        return category;
    }

    public void setCategory(ContactCategory category) {
        this.category = category;
    }

    /**
     * 联系人
     */
    @ManyToOne
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    /**
     * 联系人地址
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 邮编
     */
    @Column(length = 10)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * 电话
     */
    @Column(length = 30)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 移动电话
     */
    @Column(length = 30)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 传真
     */
    @Column(length = 30)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 电子邮箱
     */
    @Column(length = 40)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 网址
     */
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

// @Override
// protected Boolean naturalEquals(EntityBase<Integer> other) {
// Contact o = (Contact) other;
//
// if (!party.equals(o.party))
// return false;
//
// if (!Nullables.equals(category, o.category))
// return false;
//
// return true;
// }
//
// @Override
// protected Integer naturalHashCode() {
// int hash = 0;
//
// if (party == null)
// throw new NullPointerException("Contact.party is required.");
// hash += party.hashCode();
//
// if (category != null)
// hash += category.hashCode();
//
// return hash;
// }

}
