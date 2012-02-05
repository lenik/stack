package com.bee32.sem.inventory.tx.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.people.entity.Person;

/**
 * 库存调拨
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "stock_transfer_seq", allocationSize = 1)
public class StockTransfer
        extends StockJob {

    private static final long serialVersionUID = 1L;

    StockOrder source;
    StockOrder dest;
    Person transferredBy;

    StockWarehouse sourceWarehouse;
    StockWarehouse destWarehouse;

    /**
     * 调出单，科目必须为 XFER_OUT.
     *
     * @see StockOrderSubject#XFER_OUT
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "s1")
    public StockOrder getSource() {
        return source;
    }

    public void setSource(StockOrder source) {
        this.source = source;
    }

    /**
     * 拨入单，科目必须为 XFER_IN.
     *
     * @see StockOrderSubject#XFER_IN
     */
    @OneToOne
    @JoinColumn(name = "s2")
    public StockOrder getDest() {
        return dest;
    }

    public void setDest(StockOrder dest) {
        this.dest = dest;
    }

    /**
     * 调拨人
     */
    @ManyToOne
    public Person getTransferredBy() {
        return transferredBy;
    }

    public void setTransferredBy(Person transferredBy) {
        this.transferredBy = transferredBy;
    }

    @ManyToOne
    public StockWarehouse getSourceWarehouse() {
        return sourceWarehouse;
    }

    public void setSourceWarehouse(StockWarehouse sourceWarehouse) {
        this.sourceWarehouse = sourceWarehouse;
    }

    @ManyToOne
    public StockWarehouse getDestWarehouse() {
        return destWarehouse;
    }

    public void setDestWarehouse(StockWarehouse destWarehouse) {
        this.destWarehouse = destWarehouse;
    }

}
