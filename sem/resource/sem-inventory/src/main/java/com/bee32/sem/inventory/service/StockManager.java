package com.bee32.sem.inventory.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockSnapshot;
import com.bee32.sem.inventory.entity.StockSnapshotType;
import com.bee32.sem.inventory.util.StockCriteria;

public class StockManager
        extends EnterpriseService
        implements IStockManager {

    @Transactional
    @Override
    public StockSnapshot commit(StockInventory inventory, StockSnapshotType snapshotType, String description) {
        if (inventory == null)
            throw new NullPointerException("inventory");
        if (snapshotType == null)
            throw new NullPointerException("snapshotType");
        if (description == null)
            throw new NullPointerException("description");

        StockSnapshot workingBase = inventory.getWorkingBase();

        // 1. Create the new snapshot

        StockSnapshot snapshot = new StockSnapshot();
        {
            snapshot.setInventory(inventory);
            snapshot.setType(snapshotType);
            snapshot.setDescription(description);
            snapshot.setParent(workingBase);
            // snapshot.setStarting(starting);

            asFor(StockSnapshot.class).save(snapshot);
        }

        // 2. Compact the new starting
        StockOrder starting = new StockOrder(snapshot, StockOrderSubject.START);
        {
            starting.setInitTarget(snapshot);

            List<StockOrder> orders = asFor(StockOrder.class).list(StockCriteria.basedOn(workingBase));

            for (StockOrder order : orders)
                starting.merge(order);

            asFor(StockOrder.class).save(starting);
        }

        // 3. Update the inventory
        inventory.setWorkingBase(snapshot);
        asFor(StockInventory.class).update(inventory);

        // Done.
        return snapshot;
    }

    @Transactional
    @Deprecated
    @Override
    public StockSnapshot commit(StockSnapshotType snapshotType, String description) {
        return commit(StockInventory.MAIN, snapshotType, description);
    }

    @Override
    public StockSnapshot getWorkingBase(StockInventory inventory) {
        return inventory.getWorkingBase();
    }

    @Override
    public StockSnapshot getHistoryBase(Date date) {
        return null;
    }

}
