package com.bee32.sem.inventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.Identity;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.people.entity.Person;

/**
 * 仓库
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "stock_warehouse_seq", allocationSize = 1)
public class StockWarehouse
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 20;
    public static final int ADDRESS_LENGTH = 100;
    public static final int PHONE_LENGTH = 50;

    // Contact contact;
    String address;
    String phone;

    Person manager; // Person in charge.

    @Override
    public void populate(Object source) {
        if (source instanceof StockWarehouse)
            _populate((StockWarehouse) source);
        else
            super.populate(source);
    }

    protected void _populate(StockWarehouse o) {
        super._populate(o);
        address = o.address;
        phone = o.phone;
        manager = o.manager;
    }

    /**
     * 仓库名称
     */
    @NaturalId
    @Column(length = NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 仓库地址。亦用作库位的地址前缀。
     */
    @Column(length = ADDRESS_LENGTH)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 仓库的联系电话。
     */
    @Column(length = PHONE_LENGTH)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 仓库负责人。
     *
     * @see http://english.stackexchange.com/questions/32364/
     */
    @ManyToOne
    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new Identity(this);
        else
            return name;
    }

    @Override
    protected CriteriaElement selector(String prefix) {
        if (name == null)
            return null;
        else
            return new Equals(prefix + "name", name);
    }

}
