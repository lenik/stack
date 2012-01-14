package com.bee32.sem.purchase;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.purchase.entity.PurchaseAdvice;
import com.bee32.sem.purchase.entity.PurchaseRequest;

@Oid({ 3, 15, SEMOids.Biz1, SEMOids.biz1.Purchase })
public class SEMPurchaseModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/6/2";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(MakeOrder.class, "makeOrder");
        declareEntityPages(PurchaseRequest.class, "purchaseRequest");
        declareEntityPages(PurchaseAdvice.class, "purchaseAdvice");
    }

}
