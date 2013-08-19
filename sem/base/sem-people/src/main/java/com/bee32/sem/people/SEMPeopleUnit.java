package com.bee32.sem.people;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.entity.JobPerformance;
import com.bee32.sem.hr.entity.JobPost;
import com.bee32.sem.hr.entity.JobTitle;
import com.bee32.sem.hr.entity.LaborContract;
import com.bee32.sem.hr.entity.PersonEducationType;
import com.bee32.sem.hr.entity.PersonSkill;
import com.bee32.sem.hr.entity.PersonSkillCategory;
import com.bee32.sem.misc.Contract;
import com.bee32.sem.module.SEMBaseUnit;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyRecord;
import com.bee32.sem.people.entity.PartyRecordCategory;
import com.bee32.sem.people.entity.PartySidType;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.entity.PartyXP;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonLogin;
import com.bee32.sem.people.entity.PersonRole;

/**
 * SEM 人事档案数据单元
 *
 * <p lang="en">
 * SEM People Unit
 */
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

        // hr entities
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
