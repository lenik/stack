package com.bee32.sem.world.thing;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.plover.orm.ext.color.UIEntityAuto;
import com.bee32.plover.orm.ext.config.DecimalConfig;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.IFxrProvider;
import com.bee32.sem.world.monetary.MCValue;

@MappedSuperclass
@Blue
public abstract class AbstractOrderItem
        extends UIEntityAuto<Long>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    BigDecimal quantity;

    MCValue price = new MCValue();

    transient IFxrProvider fxrProvider;
    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    public AbstractOrderItem() {
    }

    public AbstractOrderItem(AbstractOrderItem item) {
        quantity = item.quantity;
        price = new MCValue(item.price);
        fxrProvider = item.fxrProvider;
        nativePrice = item.nativePrice;
        nativeTotal = item.nativeTotal;
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

    /**
     * 价格 （入库为原始价格，其余科目的价格用途未知）。
     * <p>
     * 对于合并项，如果有外币冲突，将转换为本地货币再进行汇总。
     */
    @Embedded
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
     * 总价=单价*数量。
     */
    @Transient
    public MCValue getTotal() {
        MCValue total = price.multiply(quantity);
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
            if (fxrProvider == null)
                throw new IllegalStateException("No FXR provider is set.");
            nativePrice = price.getNativeValue(fxrProvider);
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
