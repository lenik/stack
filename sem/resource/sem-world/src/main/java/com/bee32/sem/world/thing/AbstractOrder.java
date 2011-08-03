package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.ext.config.DecimalConfig;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.misc.i18n.ICurrencyAware;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.IFxrProvider;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

@MappedSuperclass
public abstract class AbstractOrder<Item extends AbstractOrderItem>
        extends TxEntity
        implements ICurrencyAware, DecimalConfig, Iterable<Item> {

    private static final long serialVersionUID = 1L;

    protected List<Item> items = new ArrayList<Item>();
    transient MCVector total;

    protected transient IFxrProvider fxrProvider;
    BigDecimal nativeTotal; // Redundant.

    /**
     * 单据明细，只读。
     *
     * @see #addItem(Item)
     * @see #removeItem(Item)
     */
    @OneToMany(mappedBy = "parent")
    @Cascade(CascadeType.ALL)
    public List<Item> getItems() {
        // TODO Collections.unmodifiableList(items);
        return items;
    }

    public void setItems(List<Item> items) {
        if (items == null)
            throw new NullPointerException("items");
        if (this.items != items) {
            this.items = items;
            invalidateTotal();
        }
    }

    public synchronized void addItem(Item item) {
        if (item == null)
            throw new NullPointerException("item");
        items.add(item);
        invalidateTotal();
    }

    public synchronized void removeItem(Item item) {
        if (item == null)
            throw new NullPointerException("item");
        items.remove(item);
        invalidateTotal();
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    /**
     * 多币种表示的金额。
     */
    @Transient
    public synchronized MCVector getTotal() {
        if (total == null) {
            total = new MCVector();
            for (Item item : items) {
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
    @Transient
    // @Column(precision = MONEY_TOTAL_PRECISION, scale = MONEY_TOTAL_SCALE)
    public synchronized BigDecimal getNativeTotal()
            throws FxrQueryException {
        if (nativeTotal == null) {
            if (fxrProvider == null)
                throw new NullPointerException("fxrProvider");

            nativeTotal = new BigDecimal(0L, MONEY_TOTAL_CONTEXT);

            for (Item item : items) {
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

    @Override
    protected void invalidate() {
        super.invalidate();
        invalidateTotal();
    }

    protected void invalidateTotal() {
        total = null;
        nativeTotal = null;
    }

}
