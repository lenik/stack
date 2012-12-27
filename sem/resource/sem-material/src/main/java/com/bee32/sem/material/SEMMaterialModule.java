package com.bee32.sem.material;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialCategory;
import com.bee32.sem.material.entity.StockLocation;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Resource, SEMOids.resource.Material })
public class SEMMaterialModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/3/6";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {

        declareEntityPages(Material.class, "material");
        declareEntityPages(MaterialCategory.class, "category");
        declareEntityPages(StockLocation.class, "location");
        declareEntityPages(StockWarehouse.class, "warehouse");
    }

}
