package com.bee32.sem.salary;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.attendance.entity.AttendanceMR;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.salary.entity.BaseBonus;
import com.bee32.sem.salary.entity.Salary;

@Oid({ 3, 15, SEMOids.Biz1, SEMOids.biz1.Wage })
public class SEMSalaryModule
        extends EnterpriseModule {
    public static final String PREFIX = "/3/15/1/6";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(Salary.class, "salary");
        declareEntityPages(AttendanceMR.class, "attendance");
        declareEntityPages(BaseBonus.class, "bonus");
    }

}
