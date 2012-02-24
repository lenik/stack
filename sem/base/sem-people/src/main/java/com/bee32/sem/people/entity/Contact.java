package com.bee32.sem.people.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.icsf.access.DefaultPermission;
import com.bee32.icsf.access.Permission;
import com.bee32.plover.ox1.color.Blue;
import com.bee32.plover.ox1.xp.EntityExt;

/**
 * 联系人。为“客户联系人”和“供应商联系人”的基类， 因为有共同的地方，所以提取本类
 */
@Entity
@Blue
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// @DiscriminatorColumn(name = "stereo", length = 4)
// @DiscriminatorValue("-")
@DefaultPermission(Permission.R_X)
@SequenceGenerator(name = "idgen", sequenceName = "contact_seq", allocationSize = 1)
public class Contact
        extends EntityExt<Integer, ContactXP> {

    private static final long serialVersionUID = 1L;

    public static final int ADDRESS_LENGTH = 100;
    public static final int POSTCODE_LENGTH = 10;
    public static final int TEL_MOBILE_FAX_LENGTH = 30;
    public static final int EMAIL_LENGTH = 40;
    public static final int WEBSITE_LENGTH = 80;
    public static final int QQ_LENGTH = 15;

    Party party;
    ContactCategory category = predefined(ContactCategories.class).NORMAL;

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
    @ManyToOne(optional = false)
    public ContactCategory getCategory() {
        return category;
    }

    public void setCategory(ContactCategory category) {
        if (category == null)
            throw new NullPointerException("category");
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
    @Column(length = ADDRESS_LENGTH)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 邮编
     */
    @Column(length = POSTCODE_LENGTH)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * 电话
     */
    @Column(length = TEL_MOBILE_FAX_LENGTH)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 移动电话
     */
    @Column(length = TEL_MOBILE_FAX_LENGTH)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 传真
     */
    @Column(length = TEL_MOBILE_FAX_LENGTH)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 电子邮箱
     */
    @Column(length = EMAIL_LENGTH)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 网址
     */
    @Column(length = WEBSITE_LENGTH)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(length = QQ_LENGTH)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

}
