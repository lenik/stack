package com.bee32.sem.inventory.tx.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.EntityBase;
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
    @NaturalId
    @OneToOne(optional = false)
    @Cascade(CascadeType.ALL)
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
    @NaturalId
    @OneToOne(optional = false)
    @Cascade(CascadeType.ALL)
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

    @Override
    protected Boolean naturalEquals(EntityBase<Long> other) {
        StockTransfer o = (StockTransfer) other;

        if (!source.equals(o.source))
            return false;

        if (!dest.equals(o.dest))
            return false;

        return true;
    }

    @Override
    protected Integer naturalHashCode() {
        int hash = 0;
        hash += source.hashCode();
        hash += dest.hashCode();
        return hash;
    }

}
