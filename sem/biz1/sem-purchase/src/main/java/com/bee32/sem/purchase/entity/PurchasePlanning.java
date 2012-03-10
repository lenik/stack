package com.bee32.sem.purchase.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Yellow;
import com.bee32.sem.make.entity.MaterialPlan;

@Yellow
@Entity
public class PurchasePlanning
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    PurchaseRequest request;
    MaterialPlan plan;

    @ManyToOne
    public PurchaseRequest getRequest() {
        return request;
    }

    public void setRequest(PurchaseRequest request) {
        this.request = request;
    }

    @OneToOne
    public MaterialPlan getPlan() {
        return plan;
    }

    public void setPlan(MaterialPlan plan) {
        this.plan = plan;
    }

}
