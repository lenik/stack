package com.bee32.sem.asset.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "stock_trade_item_seq", allocationSize = 1)
public class StockTradeItem
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    int index;

    Material material;

    BigDecimal quantity = new BigDecimal(0);
    MCValue price = new MCValue();

    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    StockTrade trade;

X-Population

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
     * 物料
     */
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

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
    public BigDecimal getNativeTotal() {
        if (nativeTotal == null) {
            BigDecimal _price;
            try {
                _price = getNativePrice();
            } catch (FxrQueryException e) {
                _price = this.price.getValue();
            }
            if (_price != null)
                nativeTotal = _price.multiply(quantity);
        }
        return nativeTotal;
    }

    public void setNativeTotal(BigDecimal nativeTotal) {
        this.nativeTotal = nativeTotal;
    }

    @ManyToOne
    public StockTrade getTrade() {
        return trade;
    }

    public void setTrade(StockTrade trade) {
        this.trade = trade;
    }

    @Transient
    protected Date getFxrDate() {
        return getCreatedDate();
    }

    void invalidateTotal() {
        nativeTotal = null;
    }

    @Override
    protected void invalidate() {
        super.invalidate();
        invalidateTotal();
    }

    @Override
    protected CEntity<?> owningEntity() {
        return trade;
    }

}
