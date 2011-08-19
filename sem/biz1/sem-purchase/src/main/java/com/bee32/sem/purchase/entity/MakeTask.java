package com.bee32.sem.purchase.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;

/**
 * 生产任务
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_task_seq", allocationSize = 1)
public class MakeTask
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    MakeOrder order;

    Date deadline;
    List<MakeTaskItem> items = new ArrayList<MakeTaskItem>();

    @ManyToOne(optional = false)
    public MakeOrder getOrder() {
        return order;
    }

    public void setOrder(MakeOrder order) {
        this.order = order;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @OneToMany(mappedBy = "task")
    @Cascade(CascadeType.ALL)
    public List<MakeTaskItem> getItems() {
        return items;
    }

    public void setItems(List<MakeTaskItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

}
