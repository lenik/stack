package com.bee32.sem.purchase.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.MomentInterval;

/**
 * 采购请求/采购申请/采购计划
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_request_seq", allocationSize = 1)
public class PurchaseRequest
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    List<MaterialPlan> plans;
    List<PurchaseRequestItem> items;

    @OneToMany(mappedBy = "purchaseRequest")
    public List<MaterialPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<MaterialPlan> plans) {
        this.plans = plans;
    }

    @OneToMany(mappedBy = "purchaseRequest", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PurchaseRequestItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseRequestItem> items) {
        if(items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(PurchaseRequestItem item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(PurchaseRequestItem item) {
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
