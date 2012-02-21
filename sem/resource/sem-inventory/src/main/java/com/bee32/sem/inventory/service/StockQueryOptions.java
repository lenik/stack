package com.bee32.sem.inventory.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
import com.bee32.sem.inventory.util.CBatch;
import com.bee32.sem.world.monetary.MCValue;

public final class StockQueryOptions
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Date timestamp = new Date();
    boolean endOfToday;
    boolean verifiedOnly = false;

    CBatch cBatch;
    MCValue price;
    Integer locationId;
    Integer warehouseId;

    boolean priceVisible;
    boolean cbatchVisible;
    boolean locationVisible;
    boolean warehouseVisible;

    public StockQueryOptions(Date timestamp, boolean endOfDay) {
        this(timestamp, null, null, null, null);
        this.endOfToday = endOfDay;
    }

    public StockQueryOptions(Date timestamp, CBatch cBatch, MCValue price, Integer locationId, Integer warehouseId) {
        setTimestamp(timestamp);
        setCBatch(cBatch);
        setPrice(price);
        setLocation(locationId);
        setWarehouse(warehouseId);
    }

    public void populate(StockQueryOptions o) {
        timestamp = o.timestamp;
        endOfToday = o.endOfToday;
        verifiedOnly = o.verifiedOnly;
        cBatch = o.cBatch;
        price = o.price;
        locationId = o.locationId;
        warehouseId = o.warehouseId;
        cbatchVisible = o.cbatchVisible;
        locationVisible = o.locationVisible;
        warehouseVisible = o.warehouseVisible;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        if (timestamp == null)
            throw new NullPointerException("timestamp");
        this.timestamp = timestamp;
    }

    public Date getTimestampOpt() {
        Date timestampOpt = timestamp;
        if (endOfToday) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timestampOpt);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            timestampOpt = calendar.getTime();
        }
        return timestampOpt;
    }

    public boolean isVerifiedOnly() {
        return verifiedOnly;
    }

    public boolean isEndOfToday() {
        return endOfToday;
    }

    public void setEndOfToday(boolean endOfToday) {
        this.endOfToday = endOfToday;
    }

    public void setVerifiedOnly(boolean verifiedOnly) {
        this.verifiedOnly = verifiedOnly;
    }

    public CBatch getCBatch() {
        return cBatch;
    }

    public void setCBatch(CBatch cbatch) {
        setCBatch(cbatch, cbatch != null);
    }

    public void setCBatch(CBatch cbatch, boolean visible) {
        this.cBatch = cbatch;
        this.cbatchVisible = cbatch != null || visible;
    }

    public boolean isCBatchVisible() {
        return cbatchVisible;
    }

    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        setPrice(price, price != null);
    }

    public void setPrice(MCValue price, boolean visible) {
        this.price = price;
        this.priceVisible = price != null || visible;
    }

    public boolean isPriceVisible() {
        return priceVisible;
    }

    public Integer getLocation() {
        return locationId;
    }

    public void setLocation(Integer location) {
        setLocation(location, location == null);
    }

    public void setLocation(Integer location, boolean visible) {
        this.locationId = location;
        this.locationVisible = location != null || visible;
    }

    public boolean isLocationVisible() {
        return locationVisible;
    }

    public Integer getWarehouse() {
        return warehouseId;
    }

    public void setWarehouse(Integer warehouse) {
        setWarehouse(warehouse, warehouse == null);
    }

    public void setWarehouse(Integer warehouse, boolean visible) {
        this.warehouseId = warehouse;
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
