package com.bee32.sem.salary;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.attendance.entity.AttendanceDR;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.salary.entity.BaseBonus;
import com.bee32.sem.salary.entity.BaseBonuses;
import com.bee32.sem.salary.entity.OvertimeTypes;

public class SEMSalarySamples
        extends NormalSamples {

    OvertimeTypes overtimeTypes = predefined(OvertimeTypes.class);
    BaseBonuses bonuses = predefined(BaseBonuses.class);

    SEMPeopleSamples people = predefined(SEMPeopleSamples.class);
//    AttendanceMR attendanceMR = new AttendanceMR();

    @Override
    protected void wireUp() {
        List<BaseBonus> subsidies = new ArrayList<BaseBonus>();
        subsidies.add(bonuses.ATTA);
        subsidies.add(bonuses.FUEL);

        AttendanceDR dayRecord = new AttendanceDR();
        dayRecord.setEmployee(people.employee);
        dayRecord.setAttType(AttendanceType.normal);
        dayRecord.setOvertime(2.0);
        dayRecord.setAbstime(0.0);

//        attendanceMR.setEmployee(people.employee);
//        attendanceMR.setDate(WageDateUtil.getTestDate());
//        attendanceMR.setAttendances(Arrays.asList(dayRecord));

    }

}
