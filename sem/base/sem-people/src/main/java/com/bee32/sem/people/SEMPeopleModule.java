package com.bee32.sem.people;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.people.entity.Org;
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
    }

}
