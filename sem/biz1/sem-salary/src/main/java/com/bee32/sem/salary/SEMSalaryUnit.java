package com.bee32.sem.salary;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.attendance.entity.AttendanceDR;
import com.bee32.sem.attendance.entity.AttendanceMR;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.salary.entity.BaseBonus;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.entity.SalaryItem;

@ImportUnit({ SEMPeopleUnit.class })
public class SEMSalaryUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(SalaryItem.class);
        add(AttendanceType.class);
        add(BaseBonus.class);
        add(AttendanceDR.class);
        add(AttendanceMR.class);
        add(Salary.class);
    }

}
