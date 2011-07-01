package com.bee32.sem.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.sem.people.entity.Person;

@Entity
public class Warehouse
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    // Contact contact;
    String address;
    String phone;

    Person personInCharge;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ManyToOne
    public Person getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(Person personInCharge) {
        this.personInCharge = personInCharge;
    }

}
