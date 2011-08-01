package com.bee32.sem.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.ext.xp.XPool30;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "stock_inventory_xp_seq", allocationSize = 1)
public class StockInventoryXP
        extends XPool30<StockInventory> {

    private static final long serialVersionUID = 1L;

}
