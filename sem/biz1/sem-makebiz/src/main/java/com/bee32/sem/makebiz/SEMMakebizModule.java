package com.bee32.sem.makebiz;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.makebiz.entity.DeliveryNote;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeProcess;
import com.bee32.sem.makebiz.entity.MakeTask;
import com.bee32.sem.makebiz.entity.MaterialPlan;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Biz1, SEMOids.biz1.Makebiz })
public class SEMMakebizModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/6/3";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(MakeOrder.class, "order");
        declareEntityPages(MakeTask.class, "task");
        declareEntityPages(MaterialPlan.class, "plan");
        declareEntityPages(DeliveryNote.class, "delivery");
        declareEntityPages(MakeProcess.class, "process");
    }

}
