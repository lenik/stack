package com.bee32.sem.inventory.tx.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.inventory.entity.AbstractStockOrder;

@MappedSuperclass
// @Entity
// @Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorColumn(name = "stereo", length = 3)
// @DiscriminatorValue("n/a")
public abstract class StockJob
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    List<? extends AbstractStockOrder<?, ?>> stockOrders = new ArrayList<>();

    @OneToMany(mappedBy = "job", targetEntity = AbstractStockOrder.class, orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<? extends AbstractStockOrder<?, ?>> getStockOrders() {
        return stockOrders;
    }

    public void setStockOrders(List<? extends AbstractStockOrder<?, ?>> stockOrders) {
        if (stockOrders == null)
            throw new NullPointerException("stockOrders");
        this.stockOrders = stockOrders;
    }

}
