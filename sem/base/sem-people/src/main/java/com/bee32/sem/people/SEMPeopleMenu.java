package com.bee32.sem.people;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.hr.entity.JobPerformance;
import com.bee32.sem.hr.entity.JobPost;
import com.bee32.sem.hr.entity.JobTitle;
import com.bee32.sem.hr.entity.PersonEducationType;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyRecordCategory;
import com.bee32.sem.people.entity.PartySidType;
import com.bee32.sem.people.entity.PartyTagname;

public class SEMPeopleMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    public static final String X_RECORDS = "records";

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(SEMPeopleModule.PREFIX_);

    public transient MenuNode PARENT = _frame_.BIZ1;

    MenuNode personAdmin = entry(PARENT, 10, "personAdmin", prefix.join("person/"));
    MenuNode orgAdmin = entry(PARENT, 11, "orgAdmin", prefix.join("org/"));
    MenuNode orgPersonAdmin = entry(PARENT, 20, "orgPersonAdmin", prefix.join("orgPerson/"));

    public MenuNode SETTINGS = menu(PARENT, 12, "SETTINGS");
    /**/MenuNode partyTag = entry(SETTINGS, 1, "partyTag", getDictIndex(PartyTagname.class));
    /**/MenuNode personSidType = entry(SETTINGS, 2, "personSidType", getDictIndex(PartySidType.class));
    /**/MenuNode orgType = entry(SETTINGS, 3, "orgType", getDictIndex(OrgType.class));
    /**/MenuNode contactCategory = entry(SETTINGS, 4, "contactCategory", getDictIndex(ContactCategory.class));
    /**/MenuNode partyRecordCategory = mode(X_RECORDS) ? entry(SETTINGS, 5, "partyRecordCategory",
            getDictIndex(PartyRecordCategory.class)) : null;

    public MenuNode EMPLOYEE = menu(_frame_.HR, 25, "EMPLOYEE");
    /**/MenuNode employeeAdmin = entry(EMPLOYEE, 1, "employeeAdmin", prefix.join("employee/"));
    /**/MenuNode EMPLOYEE_DICTS = menu(EMPLOYEE, 2, "EMPLOYEE_DICTS");
    /*    */MenuNode jobTitle = entry(EMPLOYEE_DICTS, 1, "jobTitle", getDictIndex(JobTitle.class));
    /*    */MenuNode jobPost = entry(EMPLOYEE_DICTS, 2, "jobPost", getDictIndex(JobPost.class));
    /*    */MenuNode educationType = entry(EMPLOYEE_DICTS, 3, "educationType", getDictIndex(PersonEducationType.class));
    /*    */MenuNode jobPerformance = entry(EMPLOYEE_DICTS, 4, "jobPerformance", getDictIndex(JobPerformance.class));
    /*    */MenuNode skillCategory = entry(EMPLOYEE_DICTS, 5, "skillCategory", prefix.join("skill/"));
}
