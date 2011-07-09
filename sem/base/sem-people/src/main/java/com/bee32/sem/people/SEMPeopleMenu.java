package com.bee32.sem.people;

import com.bee32.icsf.access.IcsfAccessModule;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyRecordCategory;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.entity.PersonSidType;

public class SEMPeopleMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location PEOPLE_ = WEB_APP.join(SEMPeopleModule.PREFIX_);
    static Location ACCESS_ = WEB_APP.join(IcsfAccessModule.PREFIX_);
    static Location IDENTITY_ = WEB_APP.join("/3/7/1/");

    public static MenuNode PEOPLE = menu(SEMFrameMenu.BASE, "people");
    public static MenuNode SETTINGS = menu(PEOPLE, "dict");

    static MenuNode personAdmin = entry(PEOPLE, 10, "personAdmin", PEOPLE_.join("person/index-rich.jsf"));
    static MenuNode orgAdmin = entry(PEOPLE, 20, "orgAdmin", PEOPLE_.join("org/index-rich.jsf"));

    static MenuNode partyTag = entry(SETTINGS, "partyTag", getDictIndex(PartyTagname.class));
    static MenuNode personSidType = entry(SETTINGS, "personSidType", getDictIndex(PersonSidType.class));
    static MenuNode orgType = entry(SETTINGS, "orgType", getDictIndex(OrgType.class));
    static MenuNode contactCategory = entry(SETTINGS, "contactCategory", getDictIndex(ContactCategory.class));
    static MenuNode partyRecordCategory = entry(SETTINGS, "partyRecordCategory",
            getDictIndex(PartyRecordCategory.class));

    static MenuNode permissionAdmin = entry(SEMFrameMenu.BASE, 10, "permissionAdmin",
            ACCESS_.join("r_list/index-rich.jsf"));
    static MenuNode modifyPassword = entry(SEMFrameMenu.BASE, 9, "modifyPassword", WEB_APP.join("modifyPassword.jsf"));

    static MenuNode roleAdmin = entry(SEMFrameMenu.BASE, 100, "roleAdmin", IDENTITY_.join("role/index-rich.jsf"));
    static MenuNode groupAdmin = entry(SEMFrameMenu.BASE, 110, "groupAdmin", IDENTITY_.join("group/index-rich.jsf"));
    static MenuNode userAdmin = entry(SEMFrameMenu.BASE, 120, "userAdmin", IDENTITY_.join("user/index-rich.jsf"));

    @Override
    protected void preamble() {
    }

}
