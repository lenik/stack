package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.free.IIndentedOut;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.plover.util.i18n.ICurrencyAware;
import com.bee32.sem.base.tx.TxEntity;
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

    @Override
    public void populate(Object source) {
        if (source instanceof AbstractOrder<?>)
            _populate((AbstractOrder<?>) source);
        else
            super.populate(source);
    }

    protected void _populate(AbstractOrder<?> o) {
        super._populate(o);
        items = new ArrayList<Item>(); // Don't copy (List<Item>) o.items;
        total = o.total;
        nativeTotal = o.nativeTotal;
    }

    @Transient
    public String getDisplayName() {
        return label;
    }

    /**
     * 单据明细，只读。
     *
     * @see #addItem(Item)
     * @see #removeItem(Item)
     */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @OrderBy("index")
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

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
        invalidateTotal();
    }

    public synchronized void removeItem(Item item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        item.detach();

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);

        invalidateTotal();
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
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

    public void dump(IIndentedOut out) {
        String orderType = getClass().getSimpleName();

        String serialPart = "";
        if (getSerial() != null) {
            serialPart = " [" + getSerial() + "]";
        }

        out.println(orderType + ": " + getDisplayName() + serialPart + " ON " + getBeginTime());
        out.enter();
        for (Item item : this) {
            out.printf("%-30s | %10.2f | %12s | %12s", //
                    item.getDisplayName(), //
                    item.getQuantity(), //
                    item.getPrice(), //
                    item.getTotal());
            out.println();
        }
        out.leave();
    }

    public String dump() {
        PrettyPrintStream buf = new PrettyPrintStream();
        dump(buf);
        return buf.toString();
    }

}
