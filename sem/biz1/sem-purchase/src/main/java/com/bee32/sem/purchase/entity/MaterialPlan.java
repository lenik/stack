package com.bee32.sem.purchase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
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
    StockOrder planOrder;
    String memo;

    PurchaseRequest purchaseRequest;

    public MaterialPlan() {
        planOrder = new StockOrder();
        planOrder.setSubject(StockOrderSubject.PLAN_OUT);
    }

    @ManyToOne(optional = false)
    public MakeTask getTask() {
        return task;
    }

    public void setTask(MakeTask task) {
        this.task = task;
    }

    @OneToOne(optional = false)
    @JoinColumn(name = "s1")
    @Cascade(CascadeType.ALL)
    public StockOrder getPlanOrder() {
        return planOrder;
    }

    public void setPlanOrder(StockOrder planOrder) {
        this.planOrder = planOrder;
    }

    @Column(length = MEMO_LENGTH)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @OneToOne(mappedBy = "plan")
    @Cascade(CascadeType.ALL)
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

}
