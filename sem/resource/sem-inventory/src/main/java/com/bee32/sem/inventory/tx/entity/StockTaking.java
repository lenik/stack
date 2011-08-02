package com.bee32.sem.inventory.tx.entity;

import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.service.IStockLedgerService;

/**
 * 盘点期间为 [beginTime, endTime]
 *
 * 根据期间推算出帐面订单 (book)，操作员输入盘点单（input），程序自动更新盈亏订单(delta)。
 */
public class StockTaking
        extends StockJob {

    private static final long serialVersionUID = 1L;

    StockOrder book;
    StockOrder delta;
    StockOrder input;

    IStockLedgerService stockLedgerService;

    @Transient
    public IStockLedgerService getStockLedgerService() {
        return stockLedgerService;
    }

    public void setStockLedgerService(IStockLedgerService stockLedgerService) {
        this.stockLedgerService = stockLedgerService;
    }

    @OneToOne
    public StockOrder getBook() {
        return book;
    }

    public void setBook(StockOrder book) {
        this.book = book;
    }

    @OneToOne
    public StockOrder getDelta() {
        return delta;
    }

    public void setDelta(StockOrder delta) {
        this.delta = delta;
    }

    @Transient
    public StockOrder getInput() {
        return input;
    }

    public void setInput(StockOrder input) {
        this.input = input;
    }

}
