package com.bee32.sem.people;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.entity.JobPerformance;
import com.bee32.sem.hr.entity.JobPost;
import com.bee32.sem.hr.entity.JobTitle;
import com.bee32.sem.hr.entity.PersonEducationType;
import com.bee32.sem.hr.entity.PersonSkill;
import com.bee32.sem.hr.entity.PersonSkillCategory;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.PartyRecordCategory;
import com.bee32.sem.people.entity.PartySidType;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.entity.Person;

@Oid({ 3, 15, SEMOids.Base, SEMOids.base.People })
public class SEMPeopleModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/1/5";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(Person.class, "person");
        declareEntityPages(Org.class, "org");
        declareEntityPages(ContactCategory.class, "contactCategory");
        declareEntityPages(PartyRecordCategory.class, "recordCategory");
        declareEntityPages(PartySidType.class, "sidType");
        declareEntityPages(PartyTagname.class, "tagname");
        declareEntityPages(JobPost.class, "jobPost");
        declareEntityPages(JobTitle.class, "title");
        declareEntityPages(PersonEducationType.class, "education");
        declareEntityPages(JobPerformance.class, "performance");
        declareEntityPages(PersonSkillCategory.class, "skillCategory");
        declareEntityPages(PersonSkill.class, "not defined");
        declareEntityPages(EmployeeInfo.class, "employee");
    }

}
