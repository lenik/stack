package com.bee32.sem.inventory;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialAttribute;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialStockSettings;
import com.bee32.sem.inventory.entity.MaterialXP;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockSnapshot;
import com.bee32.sem.module.SEMBaseUnit;
import com.bee32.sem.world.SEMWorldUnit;

@ImportUnit({ SEMBaseUnit.class, SEMWorldUnit.class })
public class SEMInventoryUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Material.class);
        add(MaterialXP.class);
        add(MaterialAttribute.class);
        add(MaterialCategory.class);
        add(MaterialStockSettings.class);
        add(StockLocation.class);
        add(StockSnapshot.class);
        add(StockOrder.class);
        add(StockOrderItem.class);
    }

}
