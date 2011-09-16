package com.bee32.sem.inventory.service;

import java.io.Serializable;
import java.util.Date;

import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;

public final class StockQueryOptions
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Date timestamp = new Date();

    String cbatch;
    StockLocation location;
    StockWarehouse warehouse;

    boolean cbatchMerged;
    boolean locationMerged;
    boolean warehouseMerged;

    public StockQueryOptions(Date timestamp) {
        this(timestamp, null, null, null);
    }

    public StockQueryOptions(Date timestamp, String cbatch, StockLocation location, StockWarehouse warehouse) {
        setTimestamp(timestamp);
        setCbatch(cbatch);
        setLocation(location);
        setWarehouse(warehouse);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        if (timestamp == null)
            throw new NullPointerException("timestamp");
        this.timestamp = timestamp;
    }

    public String getCbatch() {
        return cbatch;
    }

    public void setCbatch(String cbatch) {
        setCbatch(cbatch, cbatch == null);
    }

    public void setCbatch(String cbatch, boolean merge) {
        this.cbatch = cbatch;
        this.cbatchMerged = cbatch == null && merge;
    }

    public boolean isCbatchMerged() {
        return cbatchMerged;
    }

    public StockLocation getLocation() {
        return location;
    }

    public void setLocation(StockLocation location) {
        setLocation(location, location == null);
    }

    public void setLocation(StockLocation location, boolean merged) {
        this.location = location;
        this.locationMerged = location == null && merged;
    }

    public boolean isLocationMerged() {
        return locationMerged;
    }

    public StockWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouse warehouse) {
        setWarehouse(warehouse, warehouse == null);
    }

    public void setWarehouse(StockWarehouse warehouse, boolean merged) {
        this.warehouse = warehouse;
        this.warehouseMerged = warehouse == null && merged;
    }

    public boolean isWarehouseMerged() {
        return warehouseMerged;
    }

    public GroupPropertyProjection getCbatchProjection() {
        if (cbatchMerged)
            return null;
        else
            return new GroupPropertyProjection("cbatch");
    }

    public GroupPropertyProjection getLocationProjection() {
        if (locationMerged)
            return null;
        else
            return new GroupPropertyProjection("location");
    }

    public GroupPropertyProjection getWarehouseProjection() {
        if (warehouseMerged)
            return null;
        else
            return new GroupPropertyProjection("warehouse");
    }

    public GroupPropertyProjection getExpirationProjection() {
        if (cbatchMerged)
            return null;
        else
            return new GroupPropertyProjection("expirationDate");
    }

}
