package com.bee32.sem.purchase.entity;

import java.beans.Transient;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.tx.entity.StockJob;

/**
 * 送货单对应的销售出库单
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "delivery_note_take_out_seq", allocationSize = 1)
public class DeliveryNoteTakeOut
        extends StockJob {

    private static final long serialVersionUID = 1L;

    DeliveryNote deliveryNote;


    @OneToOne(optional = false)
    public DeliveryNote getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    @Transient
    public StockOrder getStockOrder() {
        if (getStockOrders().isEmpty())
            return null;
        StockOrder first = (StockOrder) getStockOrders().get(0);
        return first;
    }

    public void setStockOrder(StockOrder stockOrder) {
        List<AbstractStockOrder<?>> stockOrders = getStockOrders();
        stockOrders.clear();
        if (stockOrder != null)
            stockOrders.add(stockOrder);
    }

}
