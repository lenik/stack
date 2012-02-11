package com.bee32.sem.inventory.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.inventory.tx.entity.SimpleStockJob;

@Entity(name = "PlainStockOrder")
@DiscriminatorValue("-")
public class StockOrder
        extends AbstractStockOrder<SimpleStockJob, StockOrderItem> {

    private static final long serialVersionUID = 1L;

    public StockOrder() {
        super();
    }

    public StockOrder(StockPeriod base, StockOrderSubject subject, StockWarehouse warehouse) {
        super(base, subject, warehouse);
    }

    public StockOrder(StockPeriod base, StockOrderSubject subject) {
        super(base, subject);
    }

}
