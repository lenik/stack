package com.bee32.sem.inventory.tx.entity;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.service.IStockQuery;

/**
 * 盘点期间为 [beginTime, endTime]
 *
 * 根据期间推算出帐面订单 (book)，操作员输入盘点单（input），程序自动更新盈亏订单(delta)。
 */
public class StockTaking
        extends StockJob {

    private static final long serialVersionUID = 1L;

    StockOrder expected;
    StockOrder actual;
    StockOrder adjustment = new StockOrder();

    IStockQuery stockLedgerService;

    public StockTaking() {
    }

    @Transient
    public IStockQuery getStockLedgerService() {
        return stockLedgerService;
    }

    public void setStockLedgerService(IStockQuery stockLedgerService) {
        this.stockLedgerService = stockLedgerService;
    }

    @OneToOne
    @JoinColumn(name = "s1", nullable = false)
    @Cascade(CascadeType.ALL)
    public StockOrder getExpected() {
        return expected;
    }

    public void setExpected(StockOrder expected) {
        this.expected = expected;
    }

    @OneToOne
    @JoinColumn(name = "s2", nullable = false)
    @Cascade(CascadeType.ALL)
    public StockOrder getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(StockOrder adjustment) {
        if (adjustment == null)
            throw new NullPointerException("adjustment");
        this.adjustment = adjustment;
    }

    @Transient
    public StockOrder getActual() {
        return actual;
    }

    public void setActual(StockOrder actual) {
        this.actual = actual;
    }

}
