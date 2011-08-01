package com.bee32.sem.inventory.tx.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.people.entity.Org;

/**
 * 外协加工作业。
 *
 * @see StockOrderSubject#F_CHECKOUT
 * @see StockOrderSubject#F_CHECKIN
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "stock_outsourcing_seq", allocationSize = 1)
public class StockOutsourcing
        extends StockJob {

    private static final long serialVersionUID = 1L;

    StockOrder sentOrder;
    StockOrder receivedOrder;
    Org processedBy;

    /**
     * 委外出库单
     */
    @NaturalId
    @OneToOne(optional = false)
    @Cascade(CascadeType.ALL)
    public StockOrder getSentOrder() {
        return sentOrder;
    }

    public void setSentOrder(StockOrder sentOrder) {
        this.sentOrder = sentOrder;
    }

    /**
     * 委外入库单
     */
    @NaturalId
    @OneToOne(optional = false)
    @Cascade(CascadeType.ALL)
    public StockOrder getReceivedOrder() {
        return receivedOrder;
    }

    public void setReceivedOrder(StockOrder receivedOrder) {
        this.receivedOrder = receivedOrder;
    }

    /**
     * 加工单位
     */
    @ManyToOne
    public Org getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Org processedBy) {
        this.processedBy = processedBy;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(naturalId(sentOrder), naturalId(receivedOrder));
    }

}
