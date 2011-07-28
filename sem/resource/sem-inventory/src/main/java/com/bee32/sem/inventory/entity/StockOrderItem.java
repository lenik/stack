package com.bee32.sem.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.sem.inventory.config.BatchingConfig;
import com.bee32.sem.world.thing.AbstractOrderItem;

@Entity
@Blue
@BatchSize(size = 100)
public class StockOrderItem
        extends AbstractOrderItem {

    private static final long serialVersionUID = 1L;

    StockOrder order;
    Material material;
    String batch;
    StockLocation location;
    StockItemState state = StockItemState.NORMAL;

    public StockOrderItem() {
    }

    public StockOrderItem(StockOrderItem item) {
        super(item);
        order = item.order;
        material = item.material;
        batch = item.batch;
        location = item.location;
        state = item.state;
    }

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

        if (!order.equals(o.order))
            return false;

        if (!material.equals(o.material))
            return false;

        if (!getCBatch().equals(o.getCBatch()))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += order.hashCode();
        hash += material.hashCode();
        hash += getCBatch().hashCode();
        return hash;
    }

}
