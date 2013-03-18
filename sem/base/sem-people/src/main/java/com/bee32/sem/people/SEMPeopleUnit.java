package com.bee32.sem.people;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.hr.entity.*;
import com.bee32.sem.misc.Contract;
import com.bee32.sem.module.SEMBaseUnit;
import com.bee32.sem.people.entity.*;

@ImportUnit(SEMBaseUnit.class)
public class SEMPeopleUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Contact.class);
        add(ContactCategory.class);
        add(Org.class);
        add(OrgType.class);
        add(OrgUnit.class);
        add(Party.class);
        add(PartyXP.class);
        add(PartyRecordCategory.class);
        add(PartyRecord.class);
        add(PartyTagname.class);
        add(Person.class);
        add(PersonRole.class);
        add(PartySidType.class);
        add(PersonLogin.class);

        //hr entities
        add(EmployeeInfo.class);
        add(Contract.class);
        add(LaborContract.class);
        add(PersonSkill.class);

        add(JobPerformance.class);
        add(JobPost.class);
        add(JobTitle.class);
        add(PersonEducationType.class);
        add(PersonSkillCategory.class);
    }

}
