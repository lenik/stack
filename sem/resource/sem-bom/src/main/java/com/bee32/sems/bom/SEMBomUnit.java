package com.bee32.sems.bom;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sems.bom.entity.Part;
import com.bee32.sems.bom.entity.PartItem;

@ImportUnit({ SEMInventoryUnit.class })
public class SEMBomUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Part.class);
        add(PartItem.class);
    }

}
