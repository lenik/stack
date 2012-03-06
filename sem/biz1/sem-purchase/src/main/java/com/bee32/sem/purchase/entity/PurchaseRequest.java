package com.bee32.sem.purchase.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.process.base.ProcessEntity;

/**
 * 采购请求/采购申请/采购计划
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_request_seq", allocationSize = 1)
public class PurchaseRequest
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    List<MaterialPlan> plans = new ArrayList<MaterialPlan>();
    List<PurchaseRequestItem> items = new ArrayList<PurchaseRequestItem>();
    List<PurchaseTakeIn> takeIns = new ArrayList<PurchaseTakeIn>();

X-Population

    @OneToMany(mappedBy = "purchaseRequest")
    public List<MaterialPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<MaterialPlan> plans) {
        this.plans = plans;
    }

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PurchaseRequestItem> getItems() {
        return items;
    }

    public void setItems(List<PurchaseRequestItem> items) {
        if (items == null)
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

    @Transient
    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        for (PurchaseRequestItem item : items) {
            total = total.add(item.getQuantity());
        }
        return total;
    }

    @OneToMany(mappedBy = "purchaseRequest", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<PurchaseTakeIn> getTakeIns() {
        return takeIns;
    }

    public void setTakeIns(List<PurchaseTakeIn> takeIns) {
        if (takeIns == null)
            throw new NullPointerException("takeIns");
        this.takeIns = takeIns;
    }

    @Transient
    @Override
    public String getNumberDescription() {
        return "总数量";
    }

    @Override
    protected Number computeJudgeNumber()
            throws Exception {
        return getTotal();
    }

}
