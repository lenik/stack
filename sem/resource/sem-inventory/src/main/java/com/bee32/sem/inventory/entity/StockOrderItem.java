package com.bee32.sem.inventory.entity;

import java.util.Date;

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
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialPreferredLocation;
import com.bee32.sem.material.entity.StockLocation;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.world.thing.AbstractItem;

/**
 * 库存单据项目
 *
 * 库存单据的明细条目。
 */
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

    public StockOrderItem() {
    }

    public StockOrderItem(AbstractStockOrder<?> parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
    }

    {
        setStockItemState(StockItemState.NORMAL);
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
    }

    /**
     * 所属定单
     *
     * 列出本条目的上级定单对象。
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
     *
     * 项目出示的物料。
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
     *
     * 提示出入库的产品批次。
     */
    @Embedded
    public BatchArray getBatchArray() {
        return batchArray;
    }

    public void setBatchArray(BatchArray batchArray) {
        this.batchArray = batchArray;
    }

    /**
     * 有效期
     *
     * 物料的有效期，应该在该日期之前使用。
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
     *
     * 物料所在的库位。
     *
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
     *
     * 物料所在的仓库（应该和库位所在的仓库一致）。
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
     *
     * 在条目上标志的附加状态。
     */
    @Transient
    public StockItemState getStockItemState() {
        int state = getState();
        StockItemState stockItemState = StockItemState.forValue(state);
        return stockItemState;
    }

    public void setStockItemState(StockItemState stockItemState) {
        int state = stockItemState.getValue();
        setState(state);
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
