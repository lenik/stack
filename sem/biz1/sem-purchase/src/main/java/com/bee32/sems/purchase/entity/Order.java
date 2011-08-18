package com.bee32.sems.purchase.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.sem.process.verify.IAllowedByContext;
import com.bee32.sem.process.verify.util.AllowedBySupport;
import com.bee32.sems.crm.customer.entity.Customer;
import com.bee32.sems.org.entity.Person;

/**
 * 定单
 */
public class Order extends AllowedBySupport<Long, IAllowedByContext> {

    private Customer customer;
    private Date deliveryTime;
    private List<OrderItem> items;
    private Person creator;
    private Date createDate;


    @ManyToOne(targetEntity = Customer.class)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order", targetEntity = OrderItem.class)
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @ManyToOne(targetEntity = Person.class)
    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }
}
