package com.bee32.sem.purchase;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.bom.SEMBomUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.makebiz.entity.DeliveryNote;
import com.bee32.sem.makebiz.entity.DeliveryNoteItem;
import com.bee32.sem.makebiz.entity.DeliveryNoteTakeOut;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeOrderItem;
import com.bee32.sem.makebiz.entity.MakeTask;
import com.bee32.sem.makebiz.entity.MakeTaskItem;
import com.bee32.sem.makebiz.entity.MaterialPlan;
import com.bee32.sem.makebiz.entity.MaterialPlanItem;
import com.bee32.sem.makebiz.entity.StockPlanOrder;
import com.bee32.sem.purchase.entity.PurchaseInquiry;
import com.bee32.sem.purchase.entity.PurchaseRequest;
import com.bee32.sem.purchase.entity.PurchaseRequestItem;
import com.bee32.sem.purchase.entity.PurchaseTakeIn;

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
        add(PurchaseInquiry.class);
        add(PurchaseTakeIn.class);
        add(StockPlanOrder.class);
        add(DeliveryNote.class);
        add(DeliveryNoteItem.class);
        add(DeliveryNoteTakeOut.class);
    }

}
