package com.bee32.sem.purchase.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.inventory.tx.entity.StockJob;

/**
 * 物料需求
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "material_plan_seq", allocationSize = 1)
public class MaterialPlan
        extends StockJob {

    private static final long serialVersionUID = 1L;

    public static final int MEMO_LENGTH = 3000;

    MakeTask task;
    String memo;

    List<MaterialPlanItem> items = new ArrayList<MaterialPlanItem>();

    PurchaseRequest purchaseRequest;

    @ManyToOne(optional = false)
    public MakeTask getTask() {
        return task;
    }

    public void setTask(MakeTask task) {
        this.task = task;
    }

    @Column(length = MEMO_LENGTH)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    @ManyToOne
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

}
