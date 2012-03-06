package com.bee32.sem.inventory.service;

import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockPeriod;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.util.ConsumptionMap;
import com.bee32.sem.inventory.util.IMaterialSupplier;

// Not an entity.
public class StockQueryResult
        extends AbstractStockOrder<StockOrderItem>
        implements IMaterialSupplier {

    private static final long serialVersionUID = 1L;

    public StockQueryResult() {
        super();
    }

    public StockQueryResult(StockOrderSubject subject) {
        super(null, subject, null);
    }

    @Override
    public void declareSupply(ConsumptionMap consumptionMap) {
        for (StockOrderItem item : getItems()) {
            consumptionMap.supply(item.getMaterial(), item.getQuantity());
        }
    }

}
