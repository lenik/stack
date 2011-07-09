package com.bee32.sem.inventory.tx.entity;

import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.ext.color.UIEntityAuto;

@MappedSuperclass
public abstract class StockJob
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

}
