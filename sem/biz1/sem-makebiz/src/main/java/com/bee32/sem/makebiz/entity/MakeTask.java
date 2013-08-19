package com.bee32.sem.makebiz.entity;

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

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 生产任务
 *
 * 生产任务主控类。
 *
 * <p lang="en">
 * Make Task
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_task_seq", allocationSize = 1)
public class MakeTask
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    MakeOrder order;
    Date deadline;
    List<MakeTaskItem> items = new ArrayList<MakeTaskItem>();
    List<MaterialPlan> plans = new ArrayList<MaterialPlan>();

    @Override
    public void populate(Object source) {
        if (source instanceof MakeTask)
            _populate((MakeTask) source);
        else
            super.populate(source);
    }

    protected void _populate(MakeTask o) {
        super._populate(o);
        order = o.order;
        deadline = o.deadline;
        items = CopyUtils.copyList(o.items);
        plans = new ArrayList<MaterialPlan>(o.plans);
    }

    /**
     * 订单
     *
     * 本生产任务对应的订单。一个订单对应一个或多个生产任务。
     *
     * @return
     */
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
     * 完工时间
     *
     * 暂时不用
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * 明细
     *
     * 生产任务明细列表。
     *
     * @return
     */
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

        item.setTask(this);
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

    /**
     * 物料需求计划
     *
     * 本生产任务对应的物料计划列表。
     *
     * @return
     */
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
