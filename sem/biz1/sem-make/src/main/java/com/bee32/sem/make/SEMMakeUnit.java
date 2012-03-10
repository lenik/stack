package com.bee32.sem.make;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.bom.SEMBomUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.make.entity.DeliveryNote;
import com.bee32.sem.make.entity.DeliveryNoteItem;
import com.bee32.sem.make.entity.DeliveryNoteTakeOut;
import com.bee32.sem.make.entity.MakeOrder;
import com.bee32.sem.make.entity.MakeOrderItem;
import com.bee32.sem.make.entity.MakeTask;
import com.bee32.sem.make.entity.MakeTaskItem;
import com.bee32.sem.make.entity.MaterialPlan;
import com.bee32.sem.make.entity.MaterialPlanItem;
import com.bee32.sem.make.entity.StockPlanOrder;

@ImportUnit({ SEMBomUnit.class, SEMChanceUnit.class })
public class SEMMakeUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(MakeOrder.class);
        add(MakeOrderItem.class);
        add(MakeTask.class);
        add(MakeTaskItem.class);
        add(MaterialPlan.class);
        add(MaterialPlanItem.class);
        add(StockPlanOrder.class);
        add(DeliveryNote.class);
        add(DeliveryNoteItem.class);
        add(DeliveryNoteTakeOut.class);
    }

}
