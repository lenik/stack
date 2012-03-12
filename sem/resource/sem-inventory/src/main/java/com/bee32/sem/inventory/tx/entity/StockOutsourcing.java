package com.bee32.sem.inventory.tx.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.bee32.plover.orm.entity.CloneUtils;
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
public class StockOutsourcing
        extends StockJob {

    private static final long serialVersionUID = 1L;

    StockOrder output;
    StockOrder input;
    Org processedBy;

    @Override
    public void populate(Object source) {
        if (source instanceof StockOutsourcing)
            _populate((StockOutsourcing) source);
        else
            super.populate(source);
    }

    protected void _populate(StockOutsourcing o) {
        super._populate(o);
        output = CloneUtils.clone(o.output);
        input = CloneUtils.clone(o.input);
        processedBy = o.processedBy;
    }

    /**
     * 委外出库单
     */
    @OneToOne(optional = false/* , orphanRemoval = true */)
    @JoinColumn(name = "s1")
    // @Cascade(CascadeType.ALL)
    public StockOrder getOutput() {
        return output;
    }

    public void setOutput(StockOrder output) {
        this.output = output;
    }

    /**
     * 委外入库单
     */
    @OneToOne(/* orphanRemoval = true */)
    @JoinColumn(name = "s2")
    // @Cascade(CascadeType.ALL)
    public StockOrder getInput() {
        return input;
    }

    public void setInput(StockOrder input) {
        this.input = input;
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

}
