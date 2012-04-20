package com.bee32.sem.makebiz.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;

@Entity
@DiscriminatorValue("PLA")
public class StockPlanOrder
        extends StockOrder {

    private static final long serialVersionUID = 1L;

    public StockPlanOrder() {
        this.setSubject(StockOrderSubject.PLAN_OUT);
    }

}
