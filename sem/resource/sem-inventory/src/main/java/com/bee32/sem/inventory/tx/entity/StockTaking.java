package com.bee32.sem.inventory.tx.entity;

import javax.free.NotImplementedException;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.service.IStockQuery;

/**
 * 盘点期间为 [beginTime, endTime]
 *
 * 根据期间推算出帐面订单 (book)，操作员输入盘点单（input），程序自动更新盈亏订单(delta)。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "stock_taking_seq", allocationSize = 1)
public class StockTaking
        extends StockJob {

    private static final long serialVersionUID = 1L;

    StockOrder expected;
    StockOrder diff = new StockOrder();
    transient StockOrder actual;

    IStockQuery stockQuery;

    public StockTaking() {
    }

    @Transient
    public IStockQuery getStockQuery() {
        return stockQuery;
    }

    public void setStockQuery(IStockQuery stockQuery) {
        this.stockQuery = stockQuery;
    }

    /**
     * 账面数量
     */
    @Redundant
    @OneToOne(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "s1", nullable = false)
    public StockOrder getExpected() {
        return expected;
    }

    public void setExpected(StockOrder expected) {
        if (expected == null)
            throw new NullPointerException("expected");
        this.expected = expected;
    }

    /**
     * 盈亏数量
     */
    @OneToOne( /* fetch = FetchType.EAGER */)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "s2", nullable = false)
    public StockOrder getDiff() {
        return diff;
    }

    public void setDiff(StockOrder diff) {
        if (diff == null)
            throw new NullPointerException("diff");
        this.diff = diff;
    }

    /**
     * 盘点数量/实际数量
     */
    @Transient
    public StockOrder getActual() {
        if (actual == null)
            actual = computeActual();
        return actual;
    }

    public void setActual(StockOrder actual) {
        this.actual = actual;
    }

    StockOrder computeActual() {
        StockOrder actual = new StockOrder();
        actual.populate(expected);
        // TODO actual = expected + diff
        // return actual;
        throw new NotImplementedException();
    }

}
