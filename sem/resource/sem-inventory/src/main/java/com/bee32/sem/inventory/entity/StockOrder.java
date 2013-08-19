package com.bee32.sem.inventory.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.material.entity.StockWarehouse;

/**
 * 库存单据
 *
 * 普通库存单据，用于简单的库存作业。
 *
 * <p lang="en">
 * Stock Order
 */
@Entity(name = "PlainStockOrder")
@DiscriminatorValue("-")
public class StockOrder
        extends AbstractStockOrder<StockOrderItem> {

    private static final long serialVersionUID = 1L;

    public StockOrder() {
        super();
    }

    public StockOrder(StockOrderSubject subject) {
        super(null, subject);
    }

    public StockOrder(StockPeriod base, StockOrderSubject subject, StockWarehouse warehouse) {
        super(base, subject, warehouse);
    }

    public StockOrder(StockPeriod base, StockOrderSubject subject) {
        super(base, subject);
    }

}
