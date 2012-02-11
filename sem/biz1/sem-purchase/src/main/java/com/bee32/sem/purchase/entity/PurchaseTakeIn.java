package com.bee32.sem.purchase.entity;

import java.beans.Transient;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.tx.entity.StockJob;

/**
 * 采购请求对应的采购入库单
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_take_in_seq", allocationSize = 1)
public class PurchaseTakeIn
        extends StockJob {

    private static final long serialVersionUID = 1L;

    PurchaseRequest purchaseRequest;

    @ManyToOne(optional = false)
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    @Transient
    public StockOrder getStockOrder() {
        if (getStockOrders().isEmpty())
            return null;
        StockOrder first = (StockOrder) getStockOrders().get(0);
        return first;
    }

    public void setStockOrder(StockOrder stockOrder) {
        List<StockOrder> stockOrders = Arrays.asList(stockOrder);
        setStockOrders(stockOrders);
    }

}
