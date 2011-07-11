package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.xp.EntityExt;

/**
 * 库存快照
 */
@Entity
@Green
public class StockSnapshot
        extends EntityExt<Integer, StockSnapshotXP> {

    private static final long serialVersionUID = 1L;

    StockSnapshot previous;
    List<StockSnapshot> descendants;

    StockInventory inventory;
    StockSnapshotType type = StockSnapshotType.INITIAL;

    StockOrder starting;
    List<StockOrder> orders = new ArrayList<StockOrder>();

    /**
     * 上一个快照。可用于完整性分析，以及形成快照历史的树形结构。
     */
    @ManyToOne
    public StockSnapshot getPrevious() {
        return previous;
    }

    public void setPrevious(StockSnapshot previous) {
        this.previous = previous;
    }

    /**
     * 后续快照
     */
    @OneToMany(mappedBy = "previous")
    public List<StockSnapshot> getDescendants() {
        return descendants;
    }

    public void setDescendants(List<StockSnapshot> descendants) {
        if (descendants == null)
            throw new NullPointerException("descendants");
        this.descendants = descendants;
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
     * 快照类型（仅用于分类，没有实际功能）。
     */
    @Transient
    public StockSnapshotType getType() {
        return type;
    }

    public void setType(StockSnapshotType type) {
        if (type == null)
            throw new NullPointerException("type");
        this.type = type;
    }

    @Column(name = "type", nullable = false)
    char get_Type() {
        return type.getValue();
    }

    void set_Type(char _type) {
        this.type = StockSnapshotType.valueOf(_type);
    }

    /**
     * 上一起快照汇总后的结余单（轻微冗余）。
     */
    @Redundant
    @OneToOne(mappedBy = "initTarget")
    public StockOrder getStarting() {
        return starting;
    }

    public void setStarting(StockOrder starting) {
        if (starting == null)
            throw new NullPointerException("starting");
        this.starting = starting;
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
