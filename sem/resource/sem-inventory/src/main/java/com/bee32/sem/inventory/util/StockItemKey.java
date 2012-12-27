package com.bee32.sem.inventory.util;

import java.io.Serializable;

import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.material.entity.StockLocation;

@Deprecated
public class StockItemKey
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Serializable materialId;
    BatchArray batchArray;
    Serializable locationId;

    public StockItemKey(StockOrderItem item) {
        item.getNaturalId();
        materialId = item.getMaterial().getNaturalId();
        batchArray = item.getBatchArray();
        StockLocation location = item.getLocation();
        if (location != null)
            locationId = location.getNaturalId();
    }

}
