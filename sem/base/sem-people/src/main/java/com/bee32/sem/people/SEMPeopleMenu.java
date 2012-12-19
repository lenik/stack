package com.bee32.sem.people;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.people.dto.ContactCategoryDto;
import com.bee32.sem.people.dto.OrgTypeDto;
import com.bee32.sem.people.dto.PartyRecordCategoryDto;
import com.bee32.sem.people.dto.PartySidTypeDto;
import com.bee32.sem.people.dto.PartyTagnameDto;
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
    /**/MenuNode partyTag = entry(SETTINGS, 1, "partyTag",
            simpleDictIndex("涉众分类标签", PartyTagname.class, PartyTagnameDto.class));
    /**/MenuNode personSidType = entry(SETTINGS, 2, "personSidType",
            simpleDictIndex("涉众身份类型", PartySidType.class, PartySidTypeDto.class));
    /**/MenuNode orgType = entry(SETTINGS, 3, "orgType", simpleDictIndex("组织机构类型", OrgType.class, OrgTypeDto.class));
    /**/MenuNode contactCategory = entry(SETTINGS, 4, "contactCategory",
            simpleDictIndex("联系信息分组", ContactCategory.class, ContactCategoryDto.class));
    /**/MenuNode partyRecordCategory = mode(X_RECORDS) ? entry(SETTINGS, 5, "partyRecordCategory",
            simpleDictIndex("社会档案记录分类", PartyRecordCategory.class, PartyRecordCategoryDto.class)) : null;

    public MenuNode EMPLOYEE = menu(_frame_.HR, 25, "EMPLOYEE");
    /**/MenuNode employeeAdmin = entry(EMPLOYEE, 1, "employeeAdmin", prefix.join("employee/"));
    /**/MenuNode EMPLOYEE_DICTS = menu(EMPLOYEE, 2, "EMPLOYEE_DICTS");
    /*    */MenuNode jobTitle = entry(EMPLOYEE_DICTS, 1, "jobTitle", prefix.join("jobTitle/"));
    /*    */MenuNode jobPost = entry(EMPLOYEE_DICTS, 2, "jobPost", prefix.join("jobPost/"));
    /*    */MenuNode educationType = entry(EMPLOYEE_DICTS, 3, "educationType", prefix.join("education/"));
    /*    */MenuNode jobPerformance = entry(EMPLOYEE_DICTS, 4, "jobPerformance", prefix.join("performance/"));
    /*    */MenuNode skillCategory = entry(EMPLOYEE_DICTS, 5, "skillCategory", prefix.join("skill/"));
}
