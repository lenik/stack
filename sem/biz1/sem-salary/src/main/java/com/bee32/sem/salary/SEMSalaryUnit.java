package com.bee32.sem.salary;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.asset.SEMAssetUnit;
import com.bee32.sem.attendance.entity.AttendanceMRecord;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.process.SEMProcessUnit;
import com.bee32.sem.salary.entity.EventBonus;
import com.bee32.sem.salary.entity.MonthSalary;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.entity.SalaryElement;
import com.bee32.sem.salary.entity.SalaryElementDef;

@ImportUnit({ SEMPeopleUnit.class, SEMProcessUnit.class, SEMAssetUnit.class })
public class SEMSalaryUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {

        add(SalaryElementDef.class);
        add(Salary.class);
        add(SalaryElement.class);
        add(EventBonus.class);
        add(AttendanceMRecord.class);
        add(MonthSalary.class);
    }

}
