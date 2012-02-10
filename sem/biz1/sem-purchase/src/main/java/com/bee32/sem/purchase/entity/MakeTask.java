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

    List<MaterialPlan> plans = new ArrayList<MaterialPlan>();

    @ManyToOne(optional = false)
    public MakeOrder getOrder() {
        return order;
    }

    public void setOrder(MakeOrder order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    /**
     * 完工时间(暂时不用)
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @OneToMany(mappedBy = "task", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<MakeTaskItem> getItems() {
        return items;
    }

    public void setItems(List<MakeTaskItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(MakeTaskItem item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(MakeTaskItem item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        item.detach();

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

    @OneToMany(mappedBy = "task")
    @Cascade(CascadeType.ALL)
    public List<MaterialPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<MaterialPlan> plans) {
        if (plans == null)
            throw new NullPointerException("plans");
        this.plans = plans;
    }
}
