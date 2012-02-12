package com.bee32.sem.inventory.tx.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.inventory.entity.AbstractStockOrder;

//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorColumn(name = "stereo", length = 3)
// @DiscriminatorValue("n/a")
@SequenceGenerator(name = "idgen", sequenceName = "stock_job_seq", allocationSize = 1)
public class StockJob
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    List<AbstractStockOrder<?>> stockOrders = new ArrayList<>();

    @OneToMany(mappedBy = "job", targetEntity = AbstractStockOrder.class, orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<AbstractStockOrder<?>> getStockOrders() {
        return stockOrders;
    }

    @SuppressWarnings("unchecked")
    public void setStockOrders(List<? extends AbstractStockOrder<?>> stockOrders) {
        if (stockOrders == null)
            throw new NullPointerException("stockOrders");
        this.stockOrders = (List<AbstractStockOrder<?>>) stockOrders;
    }

}
