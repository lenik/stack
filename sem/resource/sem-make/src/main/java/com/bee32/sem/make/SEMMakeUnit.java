package com.bee32.sem.make;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sem.make.entity.MakeStepInput;
import com.bee32.sem.make.entity.MakeStepModel;
import com.bee32.sem.make.entity.MakeStepName;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.material.SEMMaterialUnit;

@ImportUnit({ SEMInventoryUnit.class, SEMQCUnit.class, SEMMaterialUnit.class })
public class SEMMakeUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Part.class);
        add(PartItem.class);
        add(MakeStepModel.class);
        add(MakeStepInput.class);
        add(MakeStepName.class);
    }

}
