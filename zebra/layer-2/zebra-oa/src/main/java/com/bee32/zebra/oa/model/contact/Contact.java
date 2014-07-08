package com.bee32.zebra.oa.model.contact;

import java.io.Serializable;

import net.bodz.bas.i18n.geo.Region;

public class Contact
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
    private String address1;
    private String address2;
    private String r1;
    private String r2;
    private String r3;
    private String country = "cn";
    private String postalCode;

    private String tel;
    private String mobile;
    private String fax;
    private String email;
    private String web;
    private String qq;

    public int getId() {
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

    public void setOrgId(int orgId) {
        (this.org = new Organization()).setId(orgId);
    }

    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    public void setOrgUnitId(int orgUnitId) {
        (this.orgUnit = new OrgUnit()).setId(orgUnitId);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setPersonId(int personId) {
        (this.person = new Person()).setId(personId);
    }

    /**
     * Contact name. Could be different to the real name.
     * 
     * <code>null</code> if there is a real name and the contact name should be the real name.
     */
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
     */
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    public String getR2() {
        return r2;
    }

    public void setR2(String r2) {
        this.r2 = r2;
    }

    public String getR3() {
        return r3;
    }

    public void setR3(String r3) {
        this.r3 = r3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
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
