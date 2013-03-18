package com.bee32.sem.chance;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.chance.entity.*;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Biz1, SEMOids.biz1.Chance })
public class SEMChanceModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/6/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(Chance.class, "chance");
        declareEntityPages(ChanceAction.class, "action");
        declareEntityPages(ChanceCategory.class, "category");
        declareEntityPages(ChanceActionStyle.class, "actionStyle");
        declareEntityPages(ChanceSourceType.class, "sourceType");
        declareEntityPages(ChanceStage.class, "stage");
        declareEntityPages(ProcurementMethod.class, "procurement");
        declareEntityPages(PurchaseRegulation.class, "purchase");

    }
}
