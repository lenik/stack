package com.bee32.sem.salary;

import java.math.BigDecimal;
import java.util.Arrays;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.attendance.entity.Attendance;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.entity.SalaryElement;
import com.bee32.sem.salary.entity.SalaryElementDef;

public class SEMSalarySamples
        extends NormalSamples {

    public final Salary test = new Salary();
    public final SalaryElementDef def1 = new SalaryElementDef();
    public final SalaryElementDef def2 = new SalaryElementDef();
    public final SalaryElement se1 = new SalaryElement();
    public final SalaryElement se2 = new SalaryElement();
    SEMPeopleSamples people = predefined(SEMPeopleSamples.class);

    @Override
    protected void wireUp() {

        def1.setOrder(1);
        def1.setCategory("基础工资");
        def1.setExpr("@基础工资");
        def1.setOrder(0);
        se1.setLabel("");
        se1.setParent(test);
        se1.setDef(def1);
        se1.setBonus(new BigDecimal(2000));

        def2.setOrder(2);
        def2.setCategory("补贴");
        def2.setLabel("全勤奖");
        def2.setExpr("@基础工资");
        def2.setOrder(1);
        se2.setLabel("全勤奖");
        se2.setDef(def2);
        se2.setParent(test);
        se2.setBonus(new BigDecimal(200));

        test.setElements(Arrays.asList(se1, se2));
        test.setEmployee(people.employee);


        Attendance dayRecord = new Attendance();
        dayRecord.setEmployee(people.employee);
        dayRecord.setOverworkTime(2 * 60);
        dayRecord.setAbsentTime(0);
    }

}
