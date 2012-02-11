package com.bee32.sem.inventory.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockPeriod;
import com.bee32.sem.inventory.entity.StockPeriodType;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.world.monetary.FxrQueryException;

public class StockManager
        extends DataService
        implements IStockManager {

    @Transactional
    @Override
    public StockPeriod pack(StockInventory inventory, StockPeriodType periodType, String description)
            throws FxrQueryException {
        if (inventory == null)
            throw new NullPointerException("inventory");
        if (periodType == null)
            throw new NullPointerException("periodType");
        if (description == null)
            throw new NullPointerException("description");

        StockPeriod workingBase = inventory.getWorkingBase();

        // 1. Create the new snapshot

        StockPeriod period = new StockPeriod();
        {
            period.setInventory(inventory);
            period.setType(periodType);
            period.setDescription(description);
            // snapshot.setStarting(starting);

            ctx.data.access(StockPeriod.class).save(period);
        }

        // 2. Compact the new starting
        List<StockOrder> packOrders = new ArrayList<StockOrder>();
        for (StockOrderSubject packSubject : Arrays.asList(//
                StockOrderSubject.PACK_M, //
                StockOrderSubject.PACK_MBLC)) {
            StockOrder packOrder = new StockOrder(period, packSubject);
            packOrders.add(packOrder);
        }

        List<StockOrder> orders = ctx.data.access(StockOrder.class).list(StockCriteria.basedOn(workingBase));
        for (StockOrder order : orders) {
            for (StockOrder packOrder : packOrders)
                packOrder.merge(order);
        }

        // period.setPackOrders(packOrders);
        ctx.data.access(StockOrder.class).saveAll(packOrders);

        // 3. Update the inventory
        inventory.setWorkingBase(period);
        ctx.data.access(StockInventory.class).update(inventory);

        // Done.
        return period;
    }

    @Transactional
    @Deprecated
    @Override
    public StockPeriod pack(StockPeriodType periodType, String description)
            throws FxrQueryException {
        return pack(StockInventory.MAIN, periodType, description);
    }

    @Override
    public StockPeriod getWorkingBase(StockInventory inventory) {
        return inventory.getWorkingBase();
    }

    @Override
    public StockPeriod getHistoryBase(Date date) {
        return null;
    }

}
