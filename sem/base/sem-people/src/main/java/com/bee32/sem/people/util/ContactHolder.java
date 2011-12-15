package com.bee32.sem.people.util;

import java.io.Serializable;

public class ContactHolder implements Serializable {
    private static final long serialVersionUID = 1L;

    String tel;
    String cellphone;

    boolean org;
    boolean person;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public boolean isOrg() {
        return org;
    }

    public void setOrg(boolean org) {
        this.org = org;
    }

    public boolean isPerson() {
        return person;
    }

    public void setPerson(boolean person) {
        this.person = person;
    }

}
