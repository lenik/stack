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
import com.bee32.sem.inventory.util.CBatch;
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
    CBatch cBatch;
    Date expirationDate;
    StockLocation location;
    StockItemState state = StockItemState.NORMAL;

    public StockOrderItem() {
    }

    public StockOrderItem(StockOrderItem item) {
        super(item);
        parent = item.parent;
        material = item.material;
        cBatch = item.cBatch.clone();
        expirationDate = item.expirationDate;
        location = item.location;
        state = item.state;
    }

    public StockOrderItem(AbstractStockOrder<?> parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
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
        return new StockItemKey(material, getCBatch());
    }

    /**
     * 批号
     */
    @Embedded
    public CBatch getCBatch() {
        return cBatch;
    }

    public void setCBatch(CBatch cBatch) {
        this.cBatch = cBatch;
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
        this.state = StockItemState.valueOf(_state);
    }

    @Override
    public StockOrderItem detach() {
        super.detach();
        parent = null;
        return this;
    }

}
