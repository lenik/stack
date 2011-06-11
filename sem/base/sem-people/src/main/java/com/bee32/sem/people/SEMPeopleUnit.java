package com.bee32.sem.people;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgContact;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyRecord;
import com.bee32.sem.people.entity.PartyRecordCategory;
import com.bee32.sem.people.entity.PartyTag;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonContact;
import com.bee32.sem.people.entity.PersonRole;
import com.bee32.sem.people.entity.PersonSidType;

@ImportUnit({ IcsfIdentityUnit.class })
public class SEMPeopleUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(ContactCategory.class);
        add(Contact.class);
        add(OrgType.class);
        add(Org.class);
        add(OrgContact.class);
        add(PartyRecordCategory.class);
        add(PartyRecord.class);
        add(PartyTag.class);
        add(Party.class);
        add(PersonRole.class);
        add(PersonSidType.class);
        add(PersonContact.class);
        add(Person.class);
    }

}
