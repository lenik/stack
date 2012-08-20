package com.bee32.sem.salary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.plover.orm.sample.SampleList;
import com.bee32.sem.attendance.entity.Attendance;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.salary.entity.BaseBonus;
import com.bee32.sem.salary.entity.BaseBonuses;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.entity.SalaryElement;
import com.bee32.sem.salary.entity.SalaryElementDef;

public class SEMSalarySamples
        extends NormalSamples {

    public final Salary test = new Salary();
    SEMPeopleSamples people = predefined(SEMPeopleSamples.class);
    BaseBonuses bonuses = predefined(BaseBonuses.class);

    @Override
    protected void wireUp() {

        SalaryElementDef sed1 = new SalaryElementDef();
        sed1.setCategory("基础工资");
        sed1.setExpr("基本日工资X出勤");
        sed1.setOrder(0);
        SalaryElement se1 = new SalaryElement();
        se1.setParent(test);
        se1.setDef(sed1);
        se1.setBonus(new BigDecimal(2000));

        SalaryElementDef sed2 = new SalaryElementDef();
        sed2.setCategory("全勤奖");
        sed2.setExpr("~~");
        sed2.setOrder(1);
        SalaryElement se2 = new SalaryElement();
        se2.setDef(sed2);
        se2.setParent(test);
        se2.setBonus(new BigDecimal(200));

        test.setElements(Arrays.asList(se1, se2));

        List<BaseBonus> subsidies = new ArrayList<BaseBonus>();
        subsidies.add(bonuses.PP);
        subsidies.add(bonuses.FUEL);

        Attendance dayRecord = new Attendance();
        dayRecord.setEmployee(people.employee);
        dayRecord.setOverworkTime(2 * 60);
        dayRecord.setAbsentTime(0);
    }

    @SuppressWarnings("deprecation")
    public void getSamples(SampleList samples){
        samples.add(test);
    }
}
