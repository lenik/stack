package com.bee32.sem.inventory.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.plover.orm.ext.types.MCValue;

@Entity
@Blue
public class StockOrderItem
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    StockOrder order;

    Material material;
    StockLocation location;
    BigDecimal quantity;
    MCValue price = new MCValue();
    StockItemState state;

    /**
     * 数量
     *
     * <p>
     * 精度限制：小数点后4位数字。如果需要超出该精度，您应考虑为对应物品采用不同的单位。
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
    @Column(scale = 16, precision = 4, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * 数量
     *
     * <p>
     * 精度限制：小数点后4位数字。如果需要超出该精度，您应考虑为对应物品采用不同的单位。
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
    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    @Embedded
    public MCValue getPrice() {
        return price;
    }

}
