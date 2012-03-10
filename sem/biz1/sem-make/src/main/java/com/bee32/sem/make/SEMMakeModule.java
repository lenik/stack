package com.bee32.sem.make;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.make.entity.MakeOrder;
import com.bee32.sem.make.entity.MakeTask;
import com.bee32.sem.make.entity.MaterialPlan;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Biz1, SEMOids.biz1.Make })
public class SEMMakeModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/6/3";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(MakeOrder.class, "make-order");
        declareEntityPages(MakeTask.class, "make-task");
        declareEntityPages(MaterialPlan.class, "material-plan");
    }

}
