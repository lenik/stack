package com.bee32.sem.inventory.entity;

import java.util.Date;

import javax.free.Nullables;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.sem.inventory.config.BatchingConfig;
import com.bee32.sem.world.thing.AbstractOrderItem;

@Entity
@BatchSize(size = 100)
@SequenceGenerator(name = "idgen", sequenceName = "stock_order_item_seq", allocationSize = 1)
public class StockOrderItem
        extends AbstractOrderItem {

    private static final long serialVersionUID = 1L;

    StockOrder parent;
    Material material;
    String batch;
    Date expirationDate;
    StockLocation location;
    StockItemState state = StockItemState.NORMAL;

    public StockOrderItem() {
    }

    public StockOrderItem(StockOrderItem item) {
        super(item);
        parent = item.parent;
        material = item.material;
        batch = item.batch;
        expirationDate = item.expirationDate;
        location = item.location;
        state = item.state;
    }

    /**
     * 所属订单
     */
    @NaturalId
    @ManyToOne(optional = false)
    public StockOrder getParent() {
        return parent;
    }

    public void setParent(StockOrder parent) {
        this.parent = parent;
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
    public String getCBatch() {
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

    @Transient
    public StockItemKey getKey() {
        return new StockItemKey(material, getCBatch());
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
            return null;
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
    protected Boolean naturalEquals(EntityBase<Long> other) {
        StockOrderItem o = (StockOrderItem) other;

        if (!Nullables.equals(parent, o.parent))
            return false;

        if (!Nullables.equals(material,o.material))
            return false;

        if (!getCBatch().equals(o.getCBatch()))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        if(parent != null)
            hash += parent.hashCode();
        if(material != null)
        hash += material.hashCode();
        hash += getCBatch().hashCode();
        return hash;
    }

}
