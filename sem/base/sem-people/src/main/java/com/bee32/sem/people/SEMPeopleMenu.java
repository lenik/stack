package com.bee32.sem.people;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyRecordCategory;
import com.bee32.sem.people.entity.PartySidType;
import com.bee32.sem.people.entity.PartyTagname;

public class SEMPeopleMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location PEOPLE_ = WEB_APP.join(SEMPeopleModule.PREFIX_);

    public static transient MenuNode PEOPLE = SEMFrameMenu.BIZ1;

    static MenuNode personAdmin = entry(PEOPLE, 10, "personAdmin", PEOPLE_.join("person/"));
    static MenuNode orgAdmin = entry(PEOPLE, 15, "orgAdmin", PEOPLE_.join("org/"));
    static MenuNode orgPersonAdmin = entry(PEOPLE, 20, "orgPersonAdmin", PEOPLE_.join("orgPerson/index-rich.jsf"));

    public static MenuNode SETTINGS = menu(PEOPLE, 30, "peopleDicts");
    /**/static MenuNode partyTag = entry(SETTINGS, 1, "partyTag", getDictIndex(PartyTagname.class));
    /**/static MenuNode personSidType = entry(SETTINGS, 2, "personSidType", getDictIndex(PartySidType.class));
    /**/static MenuNode orgType = entry(SETTINGS, 3, "orgType", getDictIndex(OrgType.class));
    /**/static MenuNode contactCategory = entry(SETTINGS, 4, "contactCategory", getDictIndex(ContactCategory.class));
    /**/static MenuNode partyRecordCategory = entry(SETTINGS, 5, "partyRecordCategory",
            getDictIndex(PartyRecordCategory.class));

}
