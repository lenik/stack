package com.bee32.sem.inventory.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.ext.config.DecimalConfig;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.inventory.service.IStockMergeStrategy;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.IFxrProvider;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

/**
 * List of stock items.
 * <p>
 * This class need not be an entity, however, it's subclassing from {@link TxEntity} to avoid of
 * multiple inheriting which is not supported by Java language.
 */
@MappedSuperclass
public class StockItemList
        extends TxEntity
        implements Iterable<StockOrderItem>, DecimalConfig {

    private static final long serialVersionUID = 1L;

    List<StockOrderItem> items = new ArrayList<StockOrderItem>();

    MCVector total;

    transient IFxrProvider fxrProvider;
    BigDecimal nativeTotal;

    /**
     * 单据明细，只读。
     *
     * @see #addItem(StockOrderItem)
     * @see #removeItem(StockOrderItem)
     */
    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    public List<StockOrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setItems(List<StockOrderItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(StockOrderItem item) {
        if (item == null)
            throw new NullPointerException("item");
        items.add(item);
        invalidateTotal();
    }

    public synchronized void removeItem(StockOrderItem item) {
        if (item == null)
            throw new NullPointerException("item");
        items.remove(item);
        invalidateTotal();
    }

    @Override
    public Iterator<StockOrderItem> iterator() {
        return items.iterator();
    }

    /**
     * 多币种表示的金额。
     */
    @Transient
    public synchronized MCVector getTotal() {
        if (total == null) {
            for (StockOrderItem item : items) {
                MCValue itemTotal = item.getTotal();
                total.add(itemTotal);
            }
        }
        return total;
    }

    /**
     * 获取外汇查询服务，该服务用于计算本地货币表示的价格和本地货币表示的金额。
     */
    @Transient
    public IFxrProvider getFxrProvider() {
        return fxrProvider;
    }

    /**
     * 设置外汇查询服务，该服务用于计算本地货币表示的价格和本地货币表示的金额。
     */
    public void setFxrProvider(IFxrProvider fxrProvider) {
        if (fxrProvider == null)
            throw new NullPointerException("fxrProvider");
        this.fxrProvider = fxrProvider;
    }

    /**
     * 【冗余】获取用本地货币表示的总金额。
     *
     * @throws FxrQueryException
     *             外汇查询异常。
     */
    @Redundant
    @Column(precision = MONEY_TOTAL_PRECISION, scale = MONEY_TOTAL_SCALE)
    public synchronized BigDecimal getNativeTotal()
            throws FxrQueryException {
        if (nativeTotal == null) {
            if (fxrProvider == null)
                throw new NullPointerException("fxrProvider");

            nativeTotal = new BigDecimal(0L, MONEY_TOTAL_CONTEXT);

            for (StockOrderItem item : items) {
                item.setFxrProvider(fxrProvider);
                BigDecimal itemNativeTotal = item.getNativePrice();
                nativeTotal = nativeTotal.add(itemNativeTotal);
            }
        }
        return nativeTotal;
    }

    public void setNativeTotal(BigDecimal nativeTotal) {
        this.nativeTotal = nativeTotal;
    }

    public void invalidateTotal() {
        total = null;
        nativeTotal = null;
        _mergeMap = null;
    }

    @Override
    protected void invalidate() {
        super.invalidate();
        invalidateTotal();
    }

    IStockMergeStrategy mergeStrategy;

    @Transient
    public IStockMergeStrategy getMergeStrategy() {
        return mergeStrategy;
    }

    public void setMergeStrategy(IStockMergeStrategy mergeStrategy) {
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
            total = total.addFTN(fxrProvider, itemTotal);

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

}
