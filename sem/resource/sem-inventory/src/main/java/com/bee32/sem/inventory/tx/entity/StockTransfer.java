package com.bee32.sem.inventory.tx.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.people.entity.Person;

/**
 * 库存调拨作业
 */
@Entity
public class StockTransfer
        extends StockJob {

    private static final long serialVersionUID = 1L;

    StockWarehouse sourceWarehouse;
    StockWarehouse destWarehouse;

    StockOrder source;
    StockOrder dest;

    Person transferredBy;

    @Override
    public void populate(Object source) {
        if (source instanceof StockTransfer)
            _populate((StockTransfer) source);
        else
            super.populate(source);
    }

    protected void _populate(StockTransfer o) {
        super._populate(o);
        source = CopyUtils.copy(o.source, this);
        dest = CopyUtils.copy(o.dest, this);
        transferredBy = o.transferredBy;
    }

    /**
     * 源仓库
     *
     * 调拨出库对应的源仓库。
     * @return
     */
    @ManyToOne
    public StockWarehouse getSourceWarehouse() {
        return sourceWarehouse;
    }

    public void setSourceWarehouse(StockWarehouse sourceWarehouse) {
        this.sourceWarehouse = sourceWarehouse;
    }

    /**
     * 目标仓库
     *
     * 调拨入库对应的目标仓库。
     * @return
     */
    @ManyToOne
    public StockWarehouse getDestWarehouse() {
        return destWarehouse;
    }

    public void setDestWarehouse(StockWarehouse destWarehouse) {
        this.destWarehouse = destWarehouse;
    }

    /**
     * 调拨出库单
     *
     * 科目必须为 XFER_OUT.
     *
     * @see StockOrderSubject#XFER_OUT
     */
    @OneToOne(optional = false/* , orphanRemoval = true */)
    @JoinColumn(name = "s1")
    // @Cascade(CascadeType.ALL)
    public StockOrder getSource() {
        return source;
    }

    public void setSource(StockOrder source) {
        this.source = source;
    }

    /**
     * 调拨入库单
     *
     * 科目必须为 XFER_IN.
     *
     * @see StockOrderSubject#XFER_IN
     */
    @OneToOne(/* orphanRemoval = true */)
    @JoinColumn(name = "s2")
    // @Cascade(CascadeType.ALL)
    public StockOrder getDest() {
        return dest;
    }

    public void setDest(StockOrder dest) {
        this.dest = dest;
    }

    /**
     * 调拨人
     *
     * 执行调拨操作的具体人员。
     */
    @ManyToOne
    public Person getTransferredBy() {
        return transferredBy;
    }

    public void setTransferredBy(Person transferredBy) {
        this.transferredBy = transferredBy;
    }

}
