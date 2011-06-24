package com.bee32.sem.people;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyRecordCategory;
import com.bee32.sem.people.entity.PartyTag;
import com.bee32.sem.people.entity.PersonSidType;

public class SEMPeopleMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location PEOPLE = WEB_APP.join(SEMPeopleModule.PREFIX_);

    @Contribution("sa")
    MenuEntry people = new MenuEntry("people");

    /**/@Contribution("sa/people")
    /**/MenuEntry dict = new MenuEntry("dict");

    /*    */@Contribution("sa/people/dict")
    /*    */MenuEntry partyTag = new MenuEntry("partyTag", getDictIndex(PartyTag.class));

    /*    */@Contribution("sa/people/dict")
    /*    */MenuEntry personSidType = new MenuEntry("personSidType", getDictIndex(PersonSidType.class));

    /*    */@Contribution("sa/people/dict")
    /*    */MenuEntry orgType = new MenuEntry("orgType", getDictIndex(OrgType.class));

    /*    */@Contribution("sa/people/dict")
    /*    */MenuEntry contactCategory = new MenuEntry("contactCategory", getDictIndex(ContactCategory.class));

    /*    */@Contribution("sa/people/dict")
    /*    */MenuEntry partyRecordCategory = new MenuEntry("partyRecordCategory", getDictIndex(PartyRecordCategory.class));

    /**/@Contribution("sa/people")
    /**/MenuEntry businessPartner = new MenuEntry("businessPartner");

    /*    */@Contribution("sa/people/businessPartner")
    /*    */MenuEntry personAdmin = new MenuEntry(1, "personAdmin", PEOPLE.join("person/index-rich.jsf"));

    /*    */@Contribution("sa/people/businessPartner")
    /*    */MenuEntry orgAdmin = new MenuEntry(1, "orgAdmin", PEOPLE.join("org/index-rich.jsf"));

    @Override
    protected void preamble() {
    }

}
