package com.bee32.sem.purchase;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.bom.SEMBomUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.purchase.entity.PurchaseInquiry;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.purchase.entity.MakeOrderItem;
import com.bee32.sem.purchase.entity.MakeTask;
import com.bee32.sem.purchase.entity.MakeTaskItem;
import com.bee32.sem.purchase.entity.MaterialPlan;
import com.bee32.sem.purchase.entity.MaterialPlanItem;
import com.bee32.sem.purchase.entity.PurchaseTakeIn;
import com.bee32.sem.purchase.entity.PlanOrder;
import com.bee32.sem.purchase.entity.PurchaseAdvice;
import com.bee32.sem.purchase.entity.PurchaseRequest;
import com.bee32.sem.purchase.entity.PurchaseRequestItem;

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
        add(MaterialPlanItem.class);
        add(PurchaseRequest.class);
        add(PurchaseRequestItem.class);
        add(PlanOrder.class);
        add(PurchaseInquiry.class);
        add(PurchaseAdvice.class);
        add(PurchaseTakeIn.class);
    }
}
