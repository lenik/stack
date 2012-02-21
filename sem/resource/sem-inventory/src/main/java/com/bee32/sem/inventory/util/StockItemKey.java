package com.bee32.sem.inventory.util;

import java.io.Serializable;

import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrderItem;

@Deprecated
public class StockItemKey
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Serializable materialId;
    CBatch cBatch;
    Serializable locationId;

    public StockItemKey(StockOrderItem item) {
        item.getNaturalId();
        materialId = item.getMaterial().getNaturalId();
        cBatch = item.getCBatch();
        StockLocation location = item.getLocation();
        if (location != null)
            locationId = location.getNaturalId();
    }

}
