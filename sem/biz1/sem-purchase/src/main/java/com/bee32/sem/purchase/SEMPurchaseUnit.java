package com.bee32.sem.purchase;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.purchase.entity.MakeOrderItem;
import com.bee32.sem.purchase.entity.MakeTask;
import com.bee32.sem.purchase.entity.MakeTaskItem;
import com.bee32.sem.purchase.entity.MaterialPlan;
import com.bee32.sem.purchase.entity.PurchaseRequest;
import com.bee32.sems.bom.SEMBomUnit;

@ImportUnit({ SEMBomUnit.class, SEMChanceUnit.class })
public class SEMPurchaseUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(MakeOrder.class);
        add(MakeOrderItem.class);
        add(MakeTask.class);
        add(MakeTaskItem.class);
        add(MaterialPlan.class);
        add(PurchaseRequest.class);
    }

}
