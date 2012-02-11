package com.bee32.sem.purchase.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;

@Entity
@DiscriminatorValue("PLA")
public class StockPlanOrder
        extends AbstractStockOrder<MaterialPlan, StockOrderItem> {

    private static final long serialVersionUID = 1L;

    public StockPlanOrder() {
        this.setSubject(StockOrderSubject.PLAN_OUT);
    }

}
