package com.bee32.sem.inventory.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.bee32.plover.criteria.hibernate.GroupPropertyProjection;
import com.bee32.plover.criteria.hibernate.ProjectionList;
import com.bee32.sem.inventory.util.BatchArray;
import com.bee32.sem.inventory.util.BatchMetadata;
import com.bee32.sem.world.monetary.MCValue;

public final class StockQueryOptions
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Date timestamp = new Date();
    boolean endOfToday;
    boolean verifiedOnly = false;

    final BatchArray batchArray = new BatchArray();
    final boolean batchVisible[] = new boolean[BatchArray.MAX_ARRAYSIZE];

    MCValue price;
    boolean priceVisible;

    Integer locationId;
    boolean locationVisible;

    Integer warehouseId;
    boolean warehouseVisible;

    boolean showAll = false;

    public StockQueryOptions(Date timestamp, boolean endOfDay) {
        this(timestamp, null, null, null);
        this.endOfToday = endOfDay;
    }

    public StockQueryOptions(Date timestamp, MCValue price, Integer locationId, Integer warehouseId) {
        setTimestamp(timestamp);
        setPrice(price);
        setLocation(locationId);
        setWarehouse(warehouseId);
    }

    public void populate(StockQueryOptions o) {
        timestamp = o.timestamp;
        endOfToday = o.endOfToday;
        verifiedOnly = o.verifiedOnly;

        price = o.price;
        priceVisible = o.priceVisible;

        locationId = o.locationId;
        locationVisible = o.locationVisible;

        warehouseId = o.warehouseId;
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

    public BatchArray getBatchArray() {
        return batchArray;
    }

    public void setBatchArray(BatchArray batchArray, boolean visible) {
        if (batchArray == null) {
            for (int i = 0; i < BatchArray.MAX_ARRAYSIZE; i++)
                this.batchArray.setBatch(i, null);
            setAllBatchVisible(false);
        } else {
            this.batchArray.populate(batchArray);
            String[] array = batchArray.getArray();
            for (int i = 0; i < array.length; i++)
                batchVisible[i] = array[i] != null || visible;
        }
    }

    public void setBatch(int index, String batch, boolean visible) {
        batchArray.setBatch(index, batch);
        batchVisible[index] = batch != null || visible;
    }

    public boolean isBatchVisible(int index) {
        return batchVisible[index];
    }

    public boolean isBatch0Visible() {
        return batchVisible[0];
    }

    public void setBatch0Visible(boolean visible) {
        batchVisible[0] = visible;
    }

    public boolean isBatch1Visible() {
        return batchVisible[1];
    }

    public void setBatch1Visible(boolean visible) {
        batchVisible[1] = visible;
    }

    public boolean isBatch2Visible() {
        return batchVisible[2];
    }

    public void setBatch2Visible(boolean visible) {
        batchVisible[2] = visible;
    }

    public boolean isBatch3Visible() {
        return batchVisible[3];
    }

    public void setBatch3Visible(boolean visible) {
        batchVisible[3] = visible;
    }

    public boolean isBatch4Visible() {
        return batchVisible[4];
    }

    public void setBatch4Visible(boolean visible) {
        batchVisible[4] = visible;
    }

    public boolean isBatch5Visible() {
        return batchVisible[5];
    }

    public void setBatch5Visible(boolean visible) {
        batchVisible[5] = visible;
    }

    public boolean isAnyBatchVisible() {
        int max = BatchMetadata.getInstance().getArraySize();
        for (int i = 0; i <= max; i++)
            if (batchVisible[i])
                return true;
        return false;
    }

    public void setAllBatchVisible(boolean visible) {
        Arrays.fill(batchVisible, visible);
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

    public void fillBatchProjections(ProjectionList projectionList) {
        if (isAnyBatchVisible()) {
            BatchMetadata metadata = BatchMetadata.getInstance();
            int n = metadata.getArraySize();
            for (int i = 0; i < n; i++) {
                if (isBatchVisible(i))
                    projectionList.add(new GroupPropertyProjection("batchArray.batch" + i));
            }
        }
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
        if (isAnyBatchVisible())
            return new GroupPropertyProjection("expirationDate");
        else
            return null;
    }

    public GroupPropertyProjection getPriceProjection() {
        if (isAnyBatchVisible())
            return new GroupPropertyProjection("price");
        else
            return null;
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

}
