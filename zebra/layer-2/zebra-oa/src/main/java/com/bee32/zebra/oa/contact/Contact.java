package com.bee32.zebra.oa.contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.i18n.geo.Region;
import net.bodz.bas.i18n.geo.Regions;
import net.bodz.bas.meta.cache.Derived;
import net.bodz.bas.repr.form.meta.TextInput;

import com.tinylily.model.base.CoEntity;

public class Contact
        extends CoEntity
        implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int N_NAME = 30;
    public static final int N_USAGE = 10;
    public static final int N_ADDRESS1 = 80;
    public static final int N_ADDRESS2 = 80;
    public static final int N_R1 = 30;
    public static final int N_R2 = 30;
    public static final int N_R3 = 30;
    public static final int N_COUNTRY = 2;
    public static final int N_POSTAL_CODE = 8;
    public static final int N_TEL = 20;
    public static final int N_MOBILE = 20;
    public static final int N_FAX = 20;
    public static final int N_EMAIL = 30;
    public static final int N_WEB = 80;
    public static final int N_QQ = 20;

    private int id;

    private Organization org;
    private OrgUnit orgUnit;
    private Person person;

    private String rename;
    private String usage;

    private Region region;
    private String country = "cn";
    private String r1;
    private String r2;
    private String r3;
    private String address1;
    private String address2;
    private String postalCode;

    private String tel;
    private String mobile;
    private String fax;
    private String email;
    private String web;
    private String qq;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Contact name. Could be different to the real name.
     * 
     * <code>null</code> if there is a real name and the contact name should be the real name.
     * 
     * @label Alternative Name
     * @label.zh.cn 别名
     */
    @TextInput(maxLength = N_NAME)
    public String getRename() {
        return rename;
    }

    public void setRename(String rename) {
        this.rename = rename;
    }

    /**
     * The usage name, could be "work", "home", in locale language.
     * 
     * <code>null</code> for the default/common usage.
     * 
     * @label Usage
     * @label.zh.cn 用途
     */
    @TextInput(maxLength = N_USAGE)
    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getRegionId() {
        return region == null ? null : region.joinId();
    }

    public void setRegionCode(String id) {
        this.region = Regions.getChinaRegion(id);
    }

    @TextInput(maxLength = N_COUNTRY)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @TextInput(maxLength = N_R1)
    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    @TextInput(maxLength = N_R2)
    public String getR2() {
        return r2;
    }

    public void setR2(String r2) {
        this.r2 = r2;
    }

    @TextInput(maxLength = N_R3)
    public String getR3() {
        return r3;
    }

    public void setR3(String r3) {
        this.r3 = r3;
    }

    /**
     * 地址（街道、小区）等。
     * 
     * @label 地址1
     */
    @TextInput(maxLength = N_ADDRESS1)
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * 地址（建筑、楼层）等。
     * 
     * @label 地址2
     */
    @TextInput(maxLength = N_ADDRESS2)
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @label 邮政编码
     */
    @TextInput(maxLength = N_POSTAL_CODE)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @label 固定电话
     */
    @TextInput(maxLength = N_TEL)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @label 移动电话
     */
    @TextInput(maxLength = N_MOBILE)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @label 传真
     */
    @TextInput(maxLength = N_FAX)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @label E-mail
     */
    @TextInput(maxLength = N_EMAIL)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @TextInput(maxLength = N_WEB)
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    /**
     * @label QQ
     */
    @TextInput(maxLength = N_QQ)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * Get the full address.
     * 
     * @label 地址
     */
    @Derived
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder(80);
        if (region != null) {
            String zhString = region.toZhString();
            sb.append(zhString);
            sb.append(" ");
        }
        if (address1 != null) {
            sb.append(address1);
            sb.append(" ");
        }
        if (address2 != null) {
            sb.append(address2);
            sb.append(" ");
        }
        String addr = sb.toString();
        return addr.trim();
    }

    /**
     * Get telephone numbers.
     * 
     * @label 电话/手机
     */
    @Derived
    public String getTels() {
        List<String> tels = new ArrayList<>();
        tels.add(tel);
        tels.add(mobile);
        // tels.add(fax);

        StringBuilder sb = new StringBuilder();
        for (String tel : tels)
            if (tel != null)
                if (!tel.isEmpty()) {
                    if (sb.length() != 0)
                        sb.append(", ");
                    sb.append(tel);
                }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (usage != null)
            sb.append("[" + usage + "]");

        if (region != null)
            sb.append(region);
        else {
            if (address1 != null)
                sb.append('\n' + address1);
            if (address2 != null)
                sb.append('\n' + address2);
            if (r1 != null)
                sb.append('\n' + r1);
            if (r2 != null)
                sb.append('\n' + r2);
            if (r3 != null)
                sb.append('\n' + r3);
            if (country != null)
                sb.append('\n' + country);
            if (postalCode != null)
                sb.append("\nPostal Code: " + postalCode);
        }

        if (tel != null)
            sb.append("\nTel: " + tel);
        if (mobile != null)
            sb.append("\nMobile: " + mobile);
        if (fax != null)
            sb.append("\nFax: " + fax);
        if (email != null)
            sb.append("\nEmail: " + email);
        if (web != null)
            sb.append("\nWeb: " + web);
        if (qq != null)
            sb.append("\nQQ: " + qq);

        return sb.toString();
    }

}
