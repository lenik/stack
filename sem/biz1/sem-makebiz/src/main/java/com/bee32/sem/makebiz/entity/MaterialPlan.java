package com.bee32.sem.makebiz.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.CopyUtils;
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
        items = CopyUtils.copyList(o.items);
    }

    /**
     * 生产任务
     *
     * 物料计划对应的生产任务。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    public MakeTask getTask() {
        return task;
    }

    public void setTask(MakeTask task) {
        this.task = task;
    }

    /**
     * [冗余]订单
     *
     * 外购产品，直接由订单生成。这里即为对应的订单。
     */
    @ManyToOne(fetch = FetchType.LAZY)
    public MakeOrder getOrder() {
        return order;
    }

    public void setOrder(MakeOrder order) {
        this.order = order;
    }

    /**
     * 明细
     *
     * 物料计划明细列表。
     */
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
