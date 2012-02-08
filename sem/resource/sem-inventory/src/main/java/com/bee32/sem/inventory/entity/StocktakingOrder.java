package com.bee32.sem.inventory.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("STK")
public class StocktakingOrder
        extends AbstractStockOrder<StocktakingOrderItem> {

    private static final long serialVersionUID = 1L;

    public StocktakingOrder() {
        super();
    }

    public StocktakingOrder(StockPeriod base, StockOrderSubject subject, StockWarehouse warehouse) {
        super(base, subject, warehouse);
    }

    public StocktakingOrder(StockPeriod base, StockOrderSubject subject) {
        super(base, subject);
    }

    {
        subject = StockOrderSubject.STKD;
    }

}
