package com.bee32.sem.salary;

import java.util.ArrayList;
import java.util.List;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.attendance.entity.Attendance;
import com.bee32.sem.people.SEMPeopleSamples;
import com.bee32.sem.salary.entity.BaseBonus;
import com.bee32.sem.salary.entity.BaseBonuses;

public class SEMSalarySamples
        extends NormalSamples {

    SEMPeopleSamples people = predefined(SEMPeopleSamples.class);
    BaseBonuses bonuses = predefined(BaseBonuses.class);

    @Override
    protected void wireUp() {
        List<BaseBonus> subsidies = new ArrayList<BaseBonus>();
        subsidies.add(bonuses.PP);
        subsidies.add(bonuses.FUEL);

        Attendance dayRecord = new Attendance();
        dayRecord.setEmployee(people.employee);
        dayRecord.setOverworkTime(2 * 60);
        dayRecord.setAbsentTime(0);
    }

}
