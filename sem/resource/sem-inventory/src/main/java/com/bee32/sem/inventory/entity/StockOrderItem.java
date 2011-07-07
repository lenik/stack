package com.bee32.sem.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Blue;

@Entity
@Blue
public class StockOrderItem
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    StockOrder order;

    float quantity;
    float discount = 1.00f;
    float price;

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
    @Column(scale = 4)
    public float getQuantity() {
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
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * 单价
     * <p>
     * 精度限制：小数点后4位数字。如果需要超出该精度，您应考虑为对应物品采用不同的装箱数量。
     */
    @Column(scale = 4)
    @Redundant
    public float getPrice() {
        return price;
    }

    /**
     * 单价
     * <p>
     * 精度限制：小数点后4位数字。如果需要超出该精度，您应考虑为对应物品采用不同的装箱数量。
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * 局部折扣
     * <p>
     * 折扣分为局部折扣和继承的折扣，局部折扣即订单项上的折扣，继承的折扣可能包括但不限于：
     * <ul>
     * <li>订单的折扣
     * <li>特殊商品的折扣
     * <li>VIP用户的折扣
     * <li>节假日折扣
     * <li>等等。
     * </ul>
     * 其中每个局部折扣的精度限制为百分比上小数点后3位数字，但加权后的折扣不受限制。
     */
    @Column(scale = 3, nullable = false)
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    /**
     * 折扣后的金额
     */
    @Transient
    public double getTotal() {
        return quantity * getPrice() * getDiscount();
    }

}
