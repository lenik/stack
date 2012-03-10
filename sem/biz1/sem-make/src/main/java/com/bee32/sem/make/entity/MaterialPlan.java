package com.bee32.sem.make.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.CloneUtils;
import com.bee32.sem.inventory.tx.entity.StockJob;

/**
 * 物料需求
 *
 * 作为库存作业的一种， stockOrders 对应为库存锁定。
 *
 * @see StockJob#getStockOrders()
 * @see StockPlanOrder
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "material_plan_seq", allocationSize = 1)
public class MaterialPlan
        extends StockJob {

    private static final long serialVersionUID = 1L;

    public static final int MEMO_LENGTH = 3000;

    MakeTask task;
    MakeOrder order;

    List<MaterialPlanItem> items = new ArrayList<MaterialPlanItem>();

    @Override
    public void populate(Object source) {
        if (source instanceof MaterialPlan)
            _populate((MaterialPlan) source);
        else
            super.populate(source);
    }

    protected void _populate(MaterialPlan o) {
        super._populate(o);
        task = o.task;
        order = o.order;
        items = CloneUtils.cloneList(o.items);
    }

    @ManyToOne(optional = false)
    public MakeTask getTask() {
        return task;
    }

    public void setTask(MakeTask task) {
        this.task = task;
    }

    @ManyToOne
    public MakeOrder getOrder() {
        return order;
    }

    public void setOrder(MakeOrder order) {
        this.order = order;
    }

    @OneToMany(mappedBy = "materialPlan", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<MaterialPlanItem> getItems() {
        return items;
    }

    public void setItems(List<MaterialPlanItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(MaterialPlanItem item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(MaterialPlanItem item) {
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

}
