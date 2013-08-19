package com.bee32.sem.makebiz;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.make.SEMMakeUnit;
import com.bee32.sem.makebiz.entity.DeliveryNote;
import com.bee32.sem.makebiz.entity.DeliveryNoteItem;
import com.bee32.sem.makebiz.entity.DeliveryNoteTakeOut;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeOrderItem;
import com.bee32.sem.makebiz.entity.MakeProcess;
import com.bee32.sem.makebiz.entity.MakeStep;
import com.bee32.sem.makebiz.entity.MakeStepItem;
import com.bee32.sem.makebiz.entity.MakeTask;
import com.bee32.sem.makebiz.entity.MakeTaskItem;
import com.bee32.sem.makebiz.entity.MaterialPlan;
import com.bee32.sem.makebiz.entity.MaterialPlanItem;
import com.bee32.sem.makebiz.entity.SerialNumber;
import com.bee32.sem.makebiz.entity.StockPlanOrder;
import com.bee32.sem.material.SEMMaterialUnit;

/**
 * SEM 生产订单数据单元
 *
 * <p lang="en">
 */
@ImportUnit({ SEMMakeUnit.class, SEMChanceUnit.class, SEMMaterialUnit.class })
public class SEMMakebizUnit
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
        add(MakeProcess.class);
        add(MakeStep.class);
        add(MakeStepItem.class);
        add(SerialNumber.class);
    }

}
