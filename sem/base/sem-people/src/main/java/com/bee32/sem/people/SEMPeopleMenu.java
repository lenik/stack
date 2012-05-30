package com.bee32.sem.people;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.hr.entity.JobPerformance;
import com.bee32.sem.hr.entity.JobPost;
import com.bee32.sem.hr.entity.JobTitle;
import com.bee32.sem.hr.entity.PersonEducationType;
import com.bee32.sem.hr.entity.PersonSkillCategory;
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

    static MenuNode INTERNALPERSON = menu(PEOPLE, 25, "internal");
    /**/static MenuNode internalPersonAdmin = entry(INTERNALPERSON, 1, "internalPersonAdmin", PEOPLE_.join("internalPerson/index-rich.jsf"));
    /**/static MenuNode INTERNALDICTS = menu(INTERNALPERSON, 2, "internalDicts");
    /*    */static MenuNode jobTitle = entry(INTERNALDICTS, 1, "jobTitle", getDictIndex(JobTitle.class));
    /*    */static MenuNode jobPost = entry(INTERNALDICTS, 2, "jobPost", getDictIndex(JobPost.class));
    /*    */static MenuNode educationType = entry(INTERNALDICTS, 3, "educationType", getDictIndex(PersonEducationType.class));
    /*    */static MenuNode jobPerformance = entry(INTERNALDICTS, 4, "jobPerformance", getDictIndex(JobPerformance.class));
    /*    */static MenuNode skillCategory = entry(INTERNALDICTS, 5, "skillCategory", getDictIndex(PersonSkillCategory.class));
}
