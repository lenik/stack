package com.bee32.sem.purchase.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.people.entity.Party;

/**
 * 生产定单
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_order_seq", allocationSize = 1)
public class MakeOrder
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    public static final int STATUS_LENGTH = 30;

    Party customer;
    Date deadline;
    String status;

    List<MakeOrderItem> items = new ArrayList<MakeOrderItem>();

    List<MakeTask> tasks = new ArrayList<MakeTask>();

    @ManyToOne(optional = false)
    public Party getCustomer() {
        return customer;
    }

    public void setCustomer(Party customer) {
        if (customer == null)
            throw new NullPointerException("customer");
        this.customer = customer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Column(length = STATUS_LENGTH)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<MakeOrderItem> getItems() {
        return items;
    }

    public void setItems(List<MakeOrderItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    public List<MakeTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MakeTask> tasks) {
        if (tasks == null)
            throw new NullPointerException("tasks");
        this.tasks = tasks;
    }

}
