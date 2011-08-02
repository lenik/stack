package com.bee32.sem.inventory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.sem.people.entity.Person;

/**
 * 仓库
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "stock_warehouse_seq", allocationSize = 1)
public class StockWarehouse
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    // Contact contact;
    String name;
    String address;
    String phone;

    Person manager; // Person in charge.

    /**
     * 仓库名称
     */
    @NaturalId
    @Column(length = 50, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        if (name.isEmpty())
            throw new IllegalArgumentException("Empty stock warehouse name.");
        this.name = name;
    }

    /**
     * 仓库地址。亦用作库位的地址前缀。
     */
    @Column(length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 仓库的联系电话。
     */
    @Column(length = 30)
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
            return new DummyId(this);
        else
            return name;
    }

    @Override
    protected CriteriaElement naturalSelector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

}
