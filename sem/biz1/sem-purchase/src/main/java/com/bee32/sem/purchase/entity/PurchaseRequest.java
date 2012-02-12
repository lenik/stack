package com.bee32.sem.purchase.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

/**
 * 采购请求/采购申请/采购计划
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_request_seq", allocationSize = 1)
public class PurchaseRequest
        extends MomentInterval
        implements IVerifiable<ISingleVerifierWithNumber> {

    private static final long serialVersionUID = 1L;

    List<MaterialPlan> plans = new ArrayList<MaterialPlan>();
    List<PurchaseRequestItem> items = new ArrayList<PurchaseRequestItem>();
    List<PurchaseTakeIn> takeIns = new ArrayList<PurchaseTakeIn>();

    public PurchaseRequest() {
        setVerifyContext(new SingleVerifierWithNumberSupport());
    }

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
    public BigDecimal getTotalPlanQuantity() {
        BigDecimal totalPlanQuantity = new BigDecimal(0);
        for (PurchaseRequestItem item : items) {
            totalPlanQuantity = totalPlanQuantity.add(item.getPlanQuantity());
        }
        return totalPlanQuantity;
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

    public static final IPropertyAccessor<BigDecimal> totalPlanQuantityProperty = _property_(//
            PurchaseRequest.class, "totalPlanQuantity");

    SingleVerifierWithNumberSupport verifyContext;

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupport verifyContext) {
        this.verifyContext = verifyContext;
        verifyContext.bind(this, totalPlanQuantityProperty, "金额");
    }

}
