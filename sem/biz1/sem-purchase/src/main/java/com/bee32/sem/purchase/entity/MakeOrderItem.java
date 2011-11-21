package com.bee32.sem.purchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.c.CEntityAuto;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.i18n.CurrencyConfig;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sems.bom.entity.Part;

/**
 * 定单明细项目
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_order_item_seq", allocationSize = 1)
public class MakeOrderItem
        extends CEntityAuto<Long>
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int EXT_PROD_NAME_LENGTH = 30;

    public static final int EXT_SPEC_LENGTH = 30;

    MakeOrder order;
    int index;
    Part part;
    Date deadline;
    BigDecimal quantity = new BigDecimal(1);
    MCValue price = new MCValue();

    BigDecimal nativePrice;
    BigDecimal nativeTotal;

    String externalProductName;
    String externalSpecification;

    @NaturalId
    @ManyToOne(optional = false)
    public MakeOrder getOrder() {
        return order;
    }

    public void setOrder(MakeOrder order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

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

    @NaturalId
    @ManyToOne(optional = false)
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        if (part == null)
            throw new NullPointerException("part");
        this.part = part;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
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
            nativePrice = price.getNativeValue(getCreatedDate());
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

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(order), //
                naturalId(part));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        return selectors(//
                selector(prefix + "order", order), //
                selector(prefix + "part", part));
    }

    /**
     * 产品外部名称
     * <p>
     * 和某个客户对应，具体对应客户在MakeOrder中
     * </p>
     * <p>
     * 某个产品对于不同客户的不同叫法，对内为同一种产品(同一个物料)
     * </p>
     *
     * @return
     */
    @Column(length = EXT_PROD_NAME_LENGTH)
    public String getExternalProductName() {
        return externalProductName;
    }

    public void setExternalProductName(String externalProductName) {
        this.externalProductName = externalProductName;
    }

    /**
     * 产品的外部技术参数要求
     * <p>
     * 和某个客户对应，具体对应客户在MakeOrder中
     * </p>
     * <p>
     * 不同的客户对某个产品有不同的技术要求，但对内为同一个产品，所以技术要求相同
     * </p>
     *
     * @return
     */
    @Column(length = EXT_SPEC_LENGTH)
    public String getExternalSpecification() {
        return externalSpecification;
    }

    public void setExternalSpecification(String externalSpecification) {
        this.externalSpecification = externalSpecification;
    }

    @Override
    public MakeOrderItem detach() {
        super.detach();
        order = null;
        return this;
    }

}
