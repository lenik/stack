package com.bee32.sem.people;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyRecordCategory;
import com.bee32.sem.people.entity.PartyTag;
import com.bee32.sem.people.entity.PersonSidType;

public class SEMPeopleMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location PEOPLE = WEB_APP.join(SEMPeopleModule.PREFIX_);

    public static MenuNode people = menu(SEMFrameMenu.BASE, "people");
    public static MenuNode peopleDict = menu(people, "dict");
    public static MenuNode businessPartner = menu(people, "businessPartner");

    // people/dict
    static MenuNode partyTag = entry(peopleDict, "partyTag", getDictIndex(PartyTag.class));
    static MenuNode personSidType = entry(peopleDict, "personSidType", getDictIndex(PersonSidType.class));
    static MenuNode orgType = entry(peopleDict, "orgType", getDictIndex(OrgType.class));
    static MenuNode contactCategory = entry(peopleDict, "contactCategory", getDictIndex(ContactCategory.class));
    static MenuNode partyRecordCategory = entry(peopleDict, "partyRecordCategory",
            getDictIndex(PartyRecordCategory.class));

    // people/businessPartner
    static MenuNode personAdmin = entry(businessPartner, 1, "personAdmin", PEOPLE.join("person/index-rich.jsf"));
    static MenuNode orgAdmin = entry(businessPartner, 1, "orgAdmin", PEOPLE.join("org/index-rich.jsf"));

    @Override
    protected void preamble() {
    }

}
