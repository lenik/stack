package com.bee32.sem.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.sem.people.entity.Person;

@Entity
public class StockWarehouse
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    // Contact contact;
    String address;
    String phone;

    Person manager; // Person in charge.

    @Column(length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(length = 30)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @see http://english.stackexchange.com/questions/32364/
     */
    @ManyToOne
    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }

}
