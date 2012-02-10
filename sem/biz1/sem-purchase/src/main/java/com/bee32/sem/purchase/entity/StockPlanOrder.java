package com.bee32.sem.purchase.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;

@Entity
@DiscriminatorValue("PLA")
public class StockPlanOrder
        extends AbstractStockOrder<StockOrderItem> {

    private static final long serialVersionUID = 1L;

    MaterialPlan plan;

    @ManyToOne
    public MaterialPlan getPlan() {
        return plan;
    }

    public void setPlan(MaterialPlan plan) {
        this.plan = plan;
    }
}
