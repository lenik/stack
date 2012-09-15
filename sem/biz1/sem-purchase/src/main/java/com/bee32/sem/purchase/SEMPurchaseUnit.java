package com.bee32.sem.purchase;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.make.SEMMakeUnit;
import com.bee32.sem.makebiz.SEMMakebizUnit;
import com.bee32.sem.purchase.entity.PurchaseInquiry;
import com.bee32.sem.purchase.entity.PurchaseRequest;
import com.bee32.sem.purchase.entity.PurchaseRequestItem;
import com.bee32.sem.purchase.entity.PurchaseTakeIn;

@ImportUnit({ SEMMakeUnit.class, SEMChanceUnit.class, SEMMakebizUnit.class })
public class SEMPurchaseUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(PurchaseRequest.class);
        add(PurchaseRequestItem.class);
        add(PurchaseInquiry.class);
        add(PurchaseTakeIn.class);
    }
}
