package com.bee32.sem.material;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.file.SEMFileUnit;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialAttribute;
import com.bee32.sem.material.entity.MaterialCategory;
import com.bee32.sem.material.entity.MaterialPreferredLocation;
import com.bee32.sem.material.entity.MaterialPrice;
import com.bee32.sem.material.entity.MaterialWarehouseOption;
import com.bee32.sem.material.entity.MaterialXP;
import com.bee32.sem.material.entity.StockLocation;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.world.SEMWorldUnit;

@ImportUnit({SEMWorldUnit.class, SEMFileUnit.class })
public class SEMMaterialUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {

        add(Material.class);
        add(MaterialXP.class);
        add(MaterialAttribute.class);
        add(MaterialCategory.class);
        add(MaterialPrice.class);

        add(StockWarehouse.class);
        add(StockLocation.class);
        add(MaterialWarehouseOption.class);
        add(MaterialPreferredLocation.class);

    }

}
