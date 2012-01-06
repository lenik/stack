package com.bee32.sem.bom;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.entity.PartItem;
import com.bee32.sem.inventory.SEMInventoryUnit;

@ImportUnit({ SEMInventoryUnit.class })
public class SEMBomUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Part.class);
        add(PartItem.class);
    }

}
