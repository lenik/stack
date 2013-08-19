package com.bee32.sem.inventory.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.material.entity.StockWarehouse;

/**
 * 盘点单
 *
 * 仓库的盘点单据。
 *
 * <p lang="en">
 * Stocktaking Order
 */
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
