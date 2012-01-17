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
    boolean verifiedOnly;

    String cBatch;
    StockLocation location;
    StockWarehouse warehouse;

    boolean cbatchVisible;
    boolean locationVisible;
    boolean warehouseVisible;

    public StockQueryOptions(Date timestamp) {
        this(timestamp, null, null, null);
    }

    public StockQueryOptions(Date timestamp, String cbatch, StockLocation location, StockWarehouse warehouse) {
        setTimestamp(timestamp);
        setCBatch(cbatch);
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

    public boolean isVerifiedOnly() {
        return verifiedOnly;
    }

    public void setVerifiedOnly(boolean verifiedOnly) {
        this.verifiedOnly = verifiedOnly;
    }

    public String getCBatch() {
        return cBatch;
    }

    public void setCBatch(String cbatch) {
        setCBatch(cbatch, cbatch != null);
    }

    public void setCBatch(String cbatch, boolean visible) {
        this.cBatch = cbatch;
        this.cbatchVisible = cbatch != null || visible;
    }

    public boolean isCBatchVisible() {
        return cbatchVisible;
    }

    public StockLocation getLocation() {
        return location;
    }

    public void setLocation(StockLocation location) {
        setLocation(location, location == null);
    }

    public void setLocation(StockLocation location, boolean visible) {
        this.location = location;
        this.locationVisible = location != null || visible;
    }

    public boolean isLocationVisible() {
        return locationVisible;
    }

    public StockWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouse warehouse) {
        setWarehouse(warehouse, warehouse == null);
    }

    public void setWarehouse(StockWarehouse warehouse, boolean visible) {
        this.warehouse = warehouse;
        this.warehouseVisible = warehouse != null || visible;
    }

    public boolean isWarehouseVisible() {
        return warehouseVisible;
    }

    public GroupPropertyProjection getCBatchProjection() {
        if (cbatchVisible)
            return new GroupPropertyProjection("CBatch");
        else
            return null;
    }

    public GroupPropertyProjection getLocationProjection() {
        if (locationVisible)
            return new GroupPropertyProjection("location");
        else
            return null;
    }

    public GroupPropertyProjection getWarehouseProjection() {
        if (warehouseVisible)
            return new GroupPropertyProjection("warehouse");
        else
            return null;
    }

    public GroupPropertyProjection getParentWarehouseProjection() {
        if (warehouseVisible)
            return new GroupPropertyProjection("parent.warehouse");
        else
            return null;
    }

    public GroupPropertyProjection getExpirationProjection() {
        if (cbatchVisible)
            return new GroupPropertyProjection("expirationDate");
        else
            return null;
    }

    public GroupPropertyProjection getPriceProjection() {
        if (cbatchVisible)
            return new GroupPropertyProjection("price");
        else
            return null;
    }

}
