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

import com.bee32.sem.inventory.entity.AbstractStockOrder;
import com.bee32.sem.process.base.ProcessEntity;

//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorColumn(name = "stereo", length = 3)
// @DiscriminatorValue("n/a")
@SequenceGenerator(name = "idgen", sequenceName = "stock_job_seq", allocationSize = 1)
public class StockJob
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    List<AbstractStockOrder<?>> stockOrders = new ArrayList<>();

X-Population

    @OneToMany(mappedBy = "job", targetEntity = AbstractStockOrder.class, orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<AbstractStockOrder<?>> getStockOrders() {
        return stockOrders;
    }

    /**
     * You should change the reference of the list. If you want to change the
     * content of the list, you may have to clear the list and insert elements
     * into it.
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    public void setStockOrders(List<? extends AbstractStockOrder<?>> stockOrders) {
        if (stockOrders == null)
            throw new NullPointerException("stockOrders");
        this.stockOrders = (List<AbstractStockOrder<?>>) stockOrders;
    }

}
