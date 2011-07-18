package com.bee32.sem.inventory.service;

import java.util.Date;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockSnapshot;

public class StockManager
        extends EnterpriseService
        implements IStockManager {

    @Override
    public StockSnapshot commit(StockInventory inventory) {
        StockSnapshot workingBase = inventory.getWorkingBase();

        asFor(StockOrder.class).list(StockCriteria.baseOf(workingBase));
        return null;
    }

    @Deprecated
    @Override
    public StockSnapshot commit() {
        return commit(StockInventory.MAIN);
    }

    @Override
    public StockSnapshot getWorkingBase(StockInventory inventory) {
        return null;
    }

    @Override
    public StockSnapshot getHistoryBase(Date date) {
        return null;
    }

}
