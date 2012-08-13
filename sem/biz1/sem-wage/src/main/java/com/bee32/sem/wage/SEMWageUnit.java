package com.bee32.sem.wage;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.attendance.entity.AttendanceDR;
import com.bee32.sem.attendance.entity.AttendanceMR;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.wage.entity.BaseBonus;
import com.bee32.sem.wage.entity.Salary;
import com.bee32.sem.wage.entity.WageSalaryItem;

@ImportUnit({ SEMPeopleUnit.class })
public class SEMWageUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(WageSalaryItem.class);
        add(AttendanceType.class);
        add(BaseBonus.class);
        add(AttendanceDR.class);
        add(AttendanceMR.class);
        add(Salary.class);
    }

}
