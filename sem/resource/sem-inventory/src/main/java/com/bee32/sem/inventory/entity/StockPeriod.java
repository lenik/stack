package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

/**
 * 库存期结
 */
@Entity
@Green
public class StockPeriod
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    StockInventory inventory;
    StockPeriodType type = StockPeriodType.INITIAL;

    boolean checkedOut;

    List<StockOrder> packOrders = new ArrayList<StockOrder>();
    List<StockOrder> orders = new ArrayList<StockOrder>();

    public StockPeriod() {
    }

    @ManyToOne(optional = false)
    public StockInventory getInventory() {
        return inventory;
    }

    public void setInventory(StockInventory inventory) {
        if (inventory == null)
            throw new NullPointerException("inventory");
        this.inventory = inventory;
    }

    /**
     * 期结类型（仅用于分类，没有实际功能）。
     */
    @Transient
    public StockPeriodType getType() {
        return type;
    }

    public void setType(StockPeriodType type) {
        if (type == null)
            throw new NullPointerException("type");
        this.type = type;
    }

    @Column(name = "type", nullable = false)
    char get_Type() {
        return type.getValue();
    }

    void set_Type(char _type) {
        this.type = StockPeriodType.valueOf(_type);
    }

    /**
     * 是否已检出为工作版本
     */
    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    /**
     * 期结余单
     */
    @Redundant
    @OneToMany(mappedBy = "spec")
    @Cascade(CascadeType.ALL)
    public List<StockOrder> getPackOrders() {
        return packOrders;
    }

    public void setPackOrders(List<StockOrder> packOrders) {
        if (packOrders == null)
            throw new NullPointerException("packOrders");
        this.packOrders = packOrders;
    }

    /**
     * 获取指定小结科目的小结订单
     */
    public StockOrder getPackOrder(StockOrderSubject packSubject) {
        if (packSubject == null)
            throw new NullPointerException("packSubject");

        for (StockOrder order : packOrders)
            if (order.subject == packSubject)
                return order;

        return null;
    }

    @OneToMany(mappedBy = "base")
    @OrderBy("createdDate DESC")
    public List<StockOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<StockOrder> orders) {
        if (orders == null)
            throw new NullPointerException("orders");
        this.orders = orders;
    }

}
