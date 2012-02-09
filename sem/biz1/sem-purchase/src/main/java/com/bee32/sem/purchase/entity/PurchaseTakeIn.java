package com.bee32.sem.purchase.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.inventory.entity.StockOrder;

/**
 * 采购请求对应的采购入库单
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "purchase_take_in_seq", allocationSize = 1)
public class PurchaseTakeIn
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    PurchaseRequest purchaseRequest;
    StockOrder stockOrder;

    @ManyToOne(optional = false)
    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

    @ManyToOne(optional = false)
    @Cascade(CascadeType.ALL)
    public StockOrder getStockOrder() {
        return stockOrder;
    }

    public void setStockOrder(StockOrder stockOrder) {
        this.stockOrder = stockOrder;
    }

}
