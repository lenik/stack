package com.bee32.zebra.oa.contact.impl;

import net.bodz.bas.err.ParseException;

import com.tinylily.model.sea.QVariantMap;

public class PersonCriteria
        extends PartyCriteria {

    public String surname;
    public char gender = '-';
    public String homeland;
    public boolean employee;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

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

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    @Override
    protected void populate(QVariantMap<String> map)
            throws ParseException {
        super.populate(map);
        surname = map.getString("surname", surname);
        employee = map.getBoolean("employee", employee);
    }

}
