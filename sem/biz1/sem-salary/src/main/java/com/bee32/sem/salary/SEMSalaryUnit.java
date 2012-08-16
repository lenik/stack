package com.bee32.sem.salary;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.attendance.entity.Attendance;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.salary.entity.BaseBonus;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.entity.SalaryElement;
import com.bee32.sem.salary.entity.SalaryElementDef;

@ImportUnit({ SEMPeopleUnit.class })
public class SEMSalaryUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AttendanceType.class);
        add(Attendance.class);

        add(SalaryElementDef.class);
        add(BaseBonus.class);
        add(Salary.class);
        add(SalaryElement.class);
    }

}
