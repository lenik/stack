package com.bee32.sem.inventory.tx.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity(name = "StockJob")
@SequenceGenerator(name = "idgen", sequenceName = "stock_job_seq", allocationSize = 1)
public class SimpleStockJob
        extends StockJob {

    private static final long serialVersionUID = 1L;

}
