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
import com.bee32.sem.people.entity.PartySidType;
import com.bee32.sem.people.entity.PartyTagname;

public class SEMPeopleMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location PEOPLE_ = WEB_APP.join(SEMPeopleModule.PREFIX_);
    static Location ACCESS_ = WEB_APP.join(IcsfAccessModule.PREFIX_);

    public static MenuNode PEOPLE = menu(SEMFrameMenu.DATA, 10, "people");
    public static MenuNode SETTINGS = menu(PEOPLE, 0, "dict");

    static MenuNode personAdmin = entry(PEOPLE, 10, "personAdmin", PEOPLE_.join("person/index-rich.jsf"));
    static MenuNode orgAdmin = entry(PEOPLE, 20, "orgAdmin", PEOPLE_.join("org/index-rich.jsf"));
    static MenuNode orgPersonAdmin = entry(PEOPLE, 30, "orgPersonAdmin", PEOPLE_.join("orgPerson/index-rich.jsf"));
    static MenuNode internalPersonAdmin = entry(PEOPLE, 30, "internalPersonAdmin",
            PEOPLE_.join("internalPerson/index-rich.jsf"));

    static MenuNode partyTag = entry(SETTINGS, 1, "partyTag", getDictIndex(PartyTagname.class));
    static MenuNode personSidType = entry(SETTINGS, 2, "personSidType", getDictIndex(PartySidType.class));
    static MenuNode orgType = entry(SETTINGS, 3, "orgType", getDictIndex(OrgType.class));
    static MenuNode contactCategory = entry(SETTINGS, 4, "contactCategory", getDictIndex(ContactCategory.class));
    static MenuNode partyRecordCategory = entry(SETTINGS, 5, "partyRecordCategory",
            getDictIndex(PartyRecordCategory.class));

    static MenuNode acl = entry(SEMFrameMenu.SECURITY, 10, "acl", ACCESS_.join("acl/index-rich.jsf"));
    static MenuNode permissionAdmin = entry(SEMFrameMenu.SECURITY, 10, "permissionAdmin",
            ACCESS_.join("r_list/index-rich.jsf"));
    static MenuNode modifyPassword = entry(SEMFrameMenu.PREFERENCES, 9, "modifyPassword",
            WEB_APP.join("modifyPassword.jsf"));

    @Override
    protected void preamble() {
    }

}
