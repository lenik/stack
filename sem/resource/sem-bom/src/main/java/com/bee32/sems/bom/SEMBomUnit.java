package com.bee32.sems.bom;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sems.bom.entity.Component;
import com.bee32.sems.bom.entity.MaterialPriceStrategy;
import com.bee32.sems.bom.entity.Product;
import com.bee32.sems.inventory.SEMInventoryUnit;
import com.bee32.sems.material.SEMMaterialUnit;

@ImportUnit({SEMInventoryUnit.class})
public class SEMBomUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Component.class);
        add(Product.class);
        add(MaterialPriceStrategy.class);
    }

}
