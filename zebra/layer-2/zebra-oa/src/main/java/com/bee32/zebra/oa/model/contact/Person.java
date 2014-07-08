package com.bee32.zebra.oa.model.contact;

public class Person
        extends Party {

    private static final long serialVersionUID = 1L;

    public static final int N_ROLE = 30;
    public static final int N_HOMELAND = 10;
    public static final int N_PASSPORT = 20;
    public static final int N_SOCIAL_SECURITY_NUM = 20;
    public static final int N_DRIVER_LICENSE_NUM = 20;

    public static final char MALE = 'm';
    public static final char FEMALE = 'f';
    // public static final char TRANSEXUAL = 't';

    private char gender = '-';
    private String homeland;

    private String passport;
    private String socialSecurityNum;
    private String driverLicenseNum;

    private boolean employee;

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getHomeland() {
        return homeland;
    }

    public void setHomeland(String homeland) {
        this.homeland = homeland;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getSocialSecurityNum() {
        return socialSecurityNum;
    }

    public void setSocialSecurityNum(String socialSecurityNum) {
        this.socialSecurityNum = socialSecurityNum;
    }

    public String getDriverLicenseNum() {
        return driverLicenseNum;
    }

    public void setDriverLicenseNum(String driverLicenseNum) {
        this.driverLicenseNum = driverLicenseNum;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

}
