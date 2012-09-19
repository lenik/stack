package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 库存期结
 *
 * 库存运行一段时期的统计小结，用于提高系统的查询效率。
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "stock_period_seq", allocationSize = 1)
public class StockPeriod
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    StockInventory inventory;
    StockPeriodType type = StockPeriodType.INITIAL;

    boolean checkedOut;

    List<AbstractStockOrder<?>> packOrders = new ArrayList<AbstractStockOrder<?>>();
    List<AbstractStockOrder<?>> orders = new ArrayList<AbstractStockOrder<?>>();

    public StockPeriod() {
    }

    @Override
    public void populate(Object source) {
        if (source instanceof StockPeriod)
            _populate((StockPeriod) source);
        else
            super.populate(source);
    }

    protected void _populate(StockPeriod o) {
        super._populate(o);
        inventory = o.inventory;
        type = o.type;
        checkedOut = o.checkedOut;
        packOrders = new ArrayList<AbstractStockOrder<?>>(o.packOrders);
        orders = new ArrayList<AbstractStockOrder<?>>(o.orders);
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
     * 期结类型
     *
     * （仅用于分类，没有实际功能）。
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

    /**
     * 期结类型
     *
     * （仅用于分类，没有实际功能）。
     */
    @Column(name = "type", nullable = false)
    public char get_type() {
        return type.getValue();
    }

    public void set_type(char _type) {
        this.type = StockPeriodType.forValue(_type);
    }

    /**
     * 检出标志
     *
     * 是否已检出为工作版本。
     */
    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    /**
     * 期结余单
     *
     * 期结相关的余单集合。
     */
    @Redundant
    @OneToMany(mappedBy = "spec", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<AbstractStockOrder<?>> getPackOrders() {
        return packOrders;
    }

    public void setPackOrders(List<AbstractStockOrder<?>> packOrders) {
        if (packOrders == null)
            throw new NullPointerException("packOrders");
        this.packOrders = packOrders;
    }

    /**
     * 获取指定小结科目的小结定单
     */
    public AbstractStockOrder<?> getPackOrder(StockOrderSubject packSubject) {
        if (packSubject == null)
            throw new NullPointerException("packSubject");

        for (AbstractStockOrder<?> order : packOrders)
            if (order.subject == packSubject)
                return order;

        return null;
    }

    /**
     * 实例单据
     *
     * 基于本期结的库存单据列表。
     */
    @OneToMany(mappedBy = "base")
    @OrderBy("createdDate DESC")
    public List<AbstractStockOrder<?>> getOrders() {
        return orders;
    }

    public void setOrders(List<AbstractStockOrder<?>> orders) {
        if (orders == null)
            throw new NullPointerException("orders");
        this.orders = orders;
    }

}
