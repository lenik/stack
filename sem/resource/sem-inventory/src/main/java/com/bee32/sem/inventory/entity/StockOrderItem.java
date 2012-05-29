package com.bee32.sem.inventory.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.sem.inventory.util.BatchArray;
import com.bee32.sem.world.thing.AbstractItem;

@BatchSize(size = 100)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 3)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "stock_order_item_seq", allocationSize = 1)
public class StockOrderItem
        extends AbstractItem {

    private static final long serialVersionUID = 1L;

    public static final int BATCH_LENGTH = 20;

    AbstractStockOrder<?> parent;
    Material material;
    BatchArray batchArray = new BatchArray();
    Date expirationDate;
    StockLocation location;
    StockItemState state = StockItemState.NORMAL;

    public StockOrderItem() {
    }

    public StockOrderItem(AbstractStockOrder<?> parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof StockOrderItem)
            _populate((StockOrderItem) source);
        else
            super.populate(source);
    }

    protected void _populate(StockOrderItem o) {
        super._populate(o);
        parent = o.parent;
        material = o.material;
        batchArray = o.batchArray.clone();
        expirationDate = o.expirationDate;
        location = o.location;
        state = o.state;
    }

    /**
     * 所属订单
     */
    @ManyToOne(optional = false)
    public AbstractStockOrder<?> getParent() {
        return parent;
    }

    public void setParent(AbstractStockOrder<?> parent) {
        this.parent = parent;
    }

    @Transient
    @Override
    protected Date getFxrDate() {
        return parent.getBeginTime();
    }

    @Transient
    @Override
    public String getDisplayName() {
        if (label != null)
            return label;
        else
            return material.getDisplayName();
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

    @Transient
    public StockItemKey getKey() {
        return new StockItemKey(material, getBatchArray());
    }

    /**
     * 批号
     */
    @Embedded
    public BatchArray getBatchArray() {
        return batchArray;
    }

    public void setBatchArray(BatchArray batchArray) {
        this.batchArray = batchArray;
    }

    /*
     * 有效期
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * 库位
     * <p>
     * 对于合并项，可能会丢失冲突的库位信息。
     *
     * @see MaterialPreferredLocation
     */
    @ManyToOne(optional = true)
    public StockLocation getLocation() {
        return location;
    }

    public void setLocation(StockLocation location) {
        this.location = location;
    }

    /**
     * 仓库
     */
    @Redundant
    @ManyToOne
    public StockWarehouse getWarehouse() {
        if (location == null)
            return parent.getWarehouse();
        else
            return location.getWarehouse();
    }

    void setWarehouse(StockWarehouse warehouse) {
        // OTF redundancy, just ignored.
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
        this.state = StockItemState.forValue(_state);
    }

    @Override
    public StockOrderItem detach() {
        super.detach();
        parent = null;
        return this;
    }

    @Override
    protected CEntity<?> owningEntity() {
        return getParent();
    }

}
