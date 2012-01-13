package com.bee32.sem.purchase.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.IJudgeNumber;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

/**
 * 采购请求/采购申请/采购计划
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_request_seq", allocationSize = 1)
public class PurchaseRequest
        extends MomentInterval
        implements
            IVerifiable<ISingleVerifierWithNumber>,
            IJudgeNumber {

    private static final long serialVersionUID = 1L;

    SingleVerifierWithNumberSupport singleVerifierWithNumberSupport = new SingleVerifierWithNumberSupport(this);

    List<MaterialPlan> plans;
    List<PurchaseRequestItem> items;

    List<OrderHolder> orderHolders;

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


    @OneToMany(mappedBy = "purchaseRequest", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<OrderHolder> getOrderHolders() {
        return orderHolders;
    }

    public void setOrderHolders(List<OrderHolder> orderHolders) {
        this.orderHolders = orderHolders;
    }


    public synchronized void addOrderHolder(OrderHolder orderHolder) {
        if (orderHolder == null)
            throw new NullPointerException("orderHolder");

        orderHolders.add(orderHolder);
    }

    public synchronized void removeOrderHolder(OrderHolder orderHolder) {
        if (orderHolder == null)
            throw new NullPointerException("orderHolder");

        int index = orderHolders.indexOf(orderHolder);
        if (index == -1)
            return /* false */;

        orderHolders.remove(index);
        orderHolder.detach();
    }

    public void setVerifyContext(SingleVerifierWithNumberSupport singleVerifierWithNumberSupport) {
        this.singleVerifierWithNumberSupport = singleVerifierWithNumberSupport;
        singleVerifierWithNumberSupport.bind(this);
    }

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return singleVerifierWithNumberSupport;
    }

    @Transient
    @Override
    public String getNumberDescription() {
        return "金额";
    }

    @Transient
    @Override
    public Number getJudgeNumber() {
        BigDecimal totalPlanQuantity = new BigDecimal(0);
        for (PurchaseRequestItem item : items) {
            totalPlanQuantity = totalPlanQuantity.add(item.getPlanQuantity());
        }

        return totalPlanQuantity;
    }

}
