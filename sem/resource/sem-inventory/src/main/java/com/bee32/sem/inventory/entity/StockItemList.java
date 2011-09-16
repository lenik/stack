package com.bee32.sem.inventory.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.inventory.service.IStockMergeStrategy;
import com.bee32.sem.inventory.service.SMS_MBLC;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.thing.AbstractOrder;

/**
 * List of stock items.
 * <p>
 * This class need not be an entity, however, it's subclassing from {@link TxEntity} to avoid of
 * multiple inheriting which is not supported by Java language.
 */
@MappedSuperclass
public class StockItemList
        extends AbstractOrder<StockOrderItem> {

    private static final long serialVersionUID = 1L;

    IStockMergeStrategy mergeStrategy = SMS_MBLC.INSTANCE;

    @Transient
    public IStockMergeStrategy getMergeStrategy() {
        return mergeStrategy;
    }

    public void setMergeStrategy(IStockMergeStrategy mergeStrategy) {
        if (mergeStrategy == null)
            throw new NullPointerException("mergeStrategy");
        this.mergeStrategy = mergeStrategy;
    }

    transient Map<Object, StockOrderItem> _mergeMap;

    @Transient
    protected synchronized Map<Object, StockOrderItem> getMergeMap()
            throws FxrQueryException {
        if (_mergeMap == null) {
            _mergeMap = new LinkedHashMap<Object, StockOrderItem>();
            _merge(_mergeMap, this);
        }
        return _mergeMap;
    }

    void _merge(Map<Object, StockOrderItem> map, StockItemList list)
            throws FxrQueryException {
        if (map == null)
            throw new NullPointerException("map");
        if (list == null)
            throw new NullPointerException("list");

        Date fxrDate = getBeginTime();

        // Compact myself.
        for (StockOrderItem item : this) {
            Object key = mergeStrategy.getMergeKey(item);
            StockOrderItem merging = map.get(key);
            if (merging == null) {
                // always create a new one. never share the object.
                merging = new StockOrderItem(item);
                continue;
            }

            // Merge siblings
            BigDecimal sumQuantity = merging.getQuantity().add(item.getQuantity());
            merging.setQuantity(sumQuantity);

            MCValue total = merging.getTotal();
            MCValue itemTotal = item.getTotal();
            total = total.addFTN(fxrDate, itemTotal, fxrDate);

            MCValue avgPrice = total.divide(sumQuantity);
            merging.setPrice(avgPrice);

            // Set conflicted location to null.
            StockLocation location = merging.getLocation();
            if (location != null) {
                if (!location.equals(item.getLocation())) {
                    location = null;
                    merging.setLocation(location);
                }
            }

        }
    }

    public synchronized void merge(StockItemList list)
            throws FxrQueryException {
        Map<Object, StockOrderItem> map = getMergeMap();
        _merge(map, list);
    }

    public StockItemList subList(Material material) {
        if (material == null)
            throw new NullPointerException("material");
        StockItemList subList = new StockItemList();
        for (StockOrderItem item : items) {
            Material m = item.getMaterial();
            if (material.equals(m))
                subList.addItem(item);
        }
        return subList;
    }

}
