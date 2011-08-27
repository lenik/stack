package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.misc.i18n.ICurrencyAware;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

@MappedSuperclass
public abstract class AbstractOrder<Item extends AbstractOrderItem>
        extends TxEntity
        implements ICurrencyAware, DecimalConfig, Iterable<Item> {

    private static final long serialVersionUID = 1L;

    protected List<Item> items = new ArrayList<Item>();
    transient MCVector total;

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
            synchronized (this) {
                if (nativeTotal == null) {
                    BigDecimal sum = new BigDecimal(0L, MONEY_TOTAL_CONTEXT);
                    for (Item item : items) {
                        BigDecimal itemNativeTotal = item.getNativePrice();
                        sum = sum.add(itemNativeTotal);
                    }
                    nativeTotal = sum;
                }
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
