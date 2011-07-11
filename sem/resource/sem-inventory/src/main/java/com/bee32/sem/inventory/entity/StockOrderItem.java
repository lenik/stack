package com.bee32.sem.inventory.entity;

import static com.bee32.plover.orm.ext.config.DecimalConfig.QTY_ITEM_PRECISION;
import static com.bee32.plover.orm.ext.config.DecimalConfig.QTY_ITEM_SCALE;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.plover.orm.ext.types.MCValue;
import com.bee32.sem.inventory.config.BatchingConfig;

@Entity
@Blue
public class StockOrderItem
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    StockOrder order;

    Material material;
    String batch;

    StockLocation location;
    BigDecimal quantity;

    MCValue price = new MCValue();
    StockItemState state = StockItemState.NORMAL;

    /**
     * 所属订单
     */
    @NaturalId
    @ManyToOne(optional = false)
    public StockOrder getOrder() {
        return order;
    }

    public void setOrder(StockOrder order) {
        this.order = order;
    }

    /**
     * 物料
     */
    @NaturalId
    @ManyToOne(optional = false)
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 合成批号（冗余，作为简化自然键结构）
     */
    @Redundant
    @NaturalId
    @Column(length = BatchingConfig.CBATCH_MAXLEN, nullable = false)
    String getCBatch() {
        return computeCanonicalBatch();
    }

    void setCBatch(String cBatch) {
        // Always compute c-batch on the fly.
    }

    protected String computeCanonicalBatch() {
        String batch = getBatch();
        if (batch == null)
            batch = "";
        return batch;
    }

    /**
     * 批号
     */
    @Column(length = BatchingConfig.BATCH_LENGTH)
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    /**
     * 库位
     *
     * @see MaterialPreferredLocation
     */
    @ManyToOne
    public StockLocation getLocation() {
        return location;
    }

    public void setLocation(StockLocation location) {
        this.location = location;
    }

    /**
     * 数量
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    /**
     * 价格 （入库为原始价格，其余科目的价格用途未知）。
     */
    @Embedded
    public MCValue getPrice() {
        return price;
    }

    public void setPrice(MCValue price) {
        if (price == null)
            throw new NullPointerException("price");
        this.price = price;
    }

    /**
     * 项目状态
     */
    @Transient
    public StockItemState getState() {
        return state;
    }

    public void setState(StockItemState state) {
        this.state = state;
    }

    @Column(name = "state", nullable = false)
    char get_State() {
        return state.getValue();
    }

    void set_State(char _state) {
        this.state = StockItemState.valueOf(_state);
    }

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        StockOrderItem o = (StockOrderItem) other;

        if (!order.equals(o.order))
            return false;

        if (!material.equals(o.material))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += order.hashCode();
        hash += material.hashCode();
        return hash;
    }

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
