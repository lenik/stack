package com.bee32.sem.people;

import com.bee32.plover.orm.ext.dict.CommonDictController;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.servlet.context.Location;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyLogCategory;
import com.bee32.sem.people.entity.PartyTag;
import com.bee32.sem.people.entity.PersonSidType;

public class SEMPeopleMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX);

    @Contribution("sa")
    MenuEntry people = new MenuEntry("people");

	    @Contribution("sa/people")
	    MenuEntry contactCategories = new MenuEntry("contactCategories", DICT.join(ContactCategory.class + "index.htm"));

	    @Contribution("sa/people")
	    MenuEntry orgTypes = new MenuEntry("orgTypes", DICT.join(OrgType.class + "index.htm"));

	    @Contribution("sa/people")
	    MenuEntry partyLogCategories = new MenuEntry("partyLogCategories", DICT.join(PartyLogCategory.class + "index.htm"));

	    @Contribution("sa/people")
	    MenuEntry partyTags = new MenuEntry("partyTags", DICT.join(PartyTag.class + "index.htm"));

	    @Contribution("sa/people")
	    MenuEntry personSidType = new MenuEntry("personSidType", DICT.join(PersonSidType.class + "index.htm"));



    @Override
    protected void preamble() {
    }

}
