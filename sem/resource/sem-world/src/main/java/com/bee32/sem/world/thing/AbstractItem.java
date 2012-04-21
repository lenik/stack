package com.bee32.sem.world.thing;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

@MappedSuperclass
@Green
public abstract class AbstractItem
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    int index;

    BigDecimal quantity = new BigDecimal(0);
    MCValue price = new MCValue();

    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    public AbstractItem() {
    }

    @Override
    public void populate(Object source) {
        if (source instanceof AbstractItem)
            _populate((AbstractItem) source);
        else
            super.populate(source);
    }

    protected void _populate(AbstractItem o) {
        super._populate(o);
        index = o.index;
        quantity = o.quantity;
        price = o.price;
        nativePrice = o.nativePrice;
        nativeTotal = o.nativeTotal;
    }

    @Transient
    public String getDisplayName() {
        return getLabel();
    }

    @Transient
    protected abstract Date getFxrDate();

    /**
     * 单据内部的序号
     */
    @Column(nullable = false)
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 数量
     *
     * <p>
     * 精度限制：小数点后4位数字。如果需要超出该精度，应考虑为对应物品采用不同的单位。
     * <p>
     * <fieldset> <legend> 关于数量的单词 Amount/Quantity/Number: </legend>
     * <ul>
     * <li>Number of ...: 可数的/整数的
     * <li>Amount of ...: 不可测量的
     * <li>Quantity of ...: 可测量的
     * <ul>
     * </fieldset>
     *
     * @see http://english.stackexchange.com/questions/9439/amount-vs-number
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
        invalidateTotal();
    }

    public void setQuantity(long quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    public void setQuantity(double quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    /**
     * 价格 （入库为原始价格，其余科目的价格用途未知）。
     * <p>
     * 对于合并项，如果有外币冲突，将转换为本地货币再进行汇总。
     */
    @Embedded
    @AttributeOverrides({
            // { price_c, price }
            @AttributeOverride(name = "currencyCode", column = @Column(name = "price_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "price")) })
    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
        invalidateTotal();
    }

    /**
     * Set price in native currency.
     */
    public final void setPrice(double price) {
        setPrice(new MCValue(CurrencyConfig.getNative(), price));
    }

    /**
     * 总价=单价*数量。
     */
    @Transient
    public MCValue getTotal() {
        MCValue total = price.multiply(quantity);
        return total;
    }

    /**
     * 【冗余】本地货币表示的价格。
     *
     * @return 本地货币表示的价格，非 <code>null</code>。
     * @throws FxrQueryException
     *             外汇查询异常。
     */
    @Redundant
    @Column(precision = MONEY_ITEM_PRECISION, scale = MONEY_ITEM_SCALE)
    public synchronized BigDecimal getNativePrice()
            throws FxrQueryException {
        if (nativePrice == null) {
            nativePrice = price.getNativeValue(getFxrDate());
        }
        return nativePrice;
    }

    void setNativePrice(BigDecimal nativePrice) {
        this.nativePrice = nativePrice;
        invalidateTotal();
    }

    /**
     * 【冗余】本地货币表示的金额。
     */
    @Redundant
    @Column(precision = MONEY_TOTAL_PRECISION, scale = MONEY_TOTAL_SCALE)
    public BigDecimal getNativeTotal()
            throws FxrQueryException {
        if (nativeTotal == null) {
            BigDecimal price = getNativePrice();
            if (price != null)
                nativeTotal = price.multiply(quantity);
        }
        return nativeTotal;
    }

    public void setNativeTotal(BigDecimal nativeTotal) {
        this.nativeTotal = nativeTotal;
    }

    void invalidateTotal() {
        nativeTotal = null;
    }

    @Override
    protected void invalidate() {
        super.invalidate();
        invalidateTotal();
    }

}
