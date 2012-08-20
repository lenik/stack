package com.bee32.sem.inventory;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Resource, SEMOids.resource.Inventory })
public class SEMInventoryModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/3/2";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(StockOrder.class, "stock");
        declareEntityPages(Material.class, "material");
        declareEntityPages(MaterialCategory.class, "category");
        declareEntityPages(StockLocation.class, "location");
        declareEntityPages(StockWarehouse.class, "warehouse");
    }

}
