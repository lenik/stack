package com.bee32.sem.inventory.process;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

@Embeddable
public class StockOrderVerifySupport
        extends SingleVerifierWithNumberSupport
        implements IStockOrderVerifyContext {

    private static final long serialVersionUID = 1L;

    IStockSubjectAware stockSubjectAware;

    public StockOrderVerifySupport() {
        super();
    }

    public StockOrderVerifySupport(Entity<?> entity) {
        super(entity);
    }

    @Override
    public void bind(Entity<?> entity) {
        super.bind(entity, StockOrder.NATIVE_TOTAL_PROPERTY, "金额");
        stockSubjectAware = new StockSubjectInProperty(entity, StockOrder.SUBJECT_PROPERTY);
    }

    @Override
    public void populate(Object source) {
        super.populate(source);
        if (source instanceof StockOrderVerifySupport) {
            StockOrderVerifySupport o = (StockOrderVerifySupport) source;
            this.stockSubjectAware = o.stockSubjectAware;
        }
    }

    @Transient
    @Override
    public StockOrderSubject getSubject() {
        return stockSubjectAware.getSubject();
    }

}
