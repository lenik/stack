package com.bee32.sem.inventory.entity;

import java.util.List;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
@Green
public class StockSnapshot
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    StockSnapshotType type;

    StockOrder baseOrder;
    List<StockOrder> orders;

}
