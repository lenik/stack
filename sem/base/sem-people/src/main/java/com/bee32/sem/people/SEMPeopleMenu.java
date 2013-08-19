package com.bee32.sem.people;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyRecordCategory;
import com.bee32.sem.people.entity.PartySidType;
import com.bee32.sem.people.entity.PartyTagname;

/**
 * SEM 业务伙伴菜单
 *
 * <p lang="en">
 * SEM People Menu
 */
public class SEMPeopleMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    public static final String X_RECORDS = "records";

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(SEMPeopleModule.PREFIX_);

    public transient MenuNode PARENT = _frame_.BIZ1;

    /**
     * 人员企业组织参数
     *
     * <p lang="en">
     * Settings
     */
    public MenuNode SETTINGS = menu(PARENT, 10, "SETTINGS");

    /**
     * 涉众分类标签
     *
     * <p lang="en">
     * Party Tag
     */
    /**/MenuNode partyTag = entry(SETTINGS, 1, "partyTag", simpleDictIndex("涉众分类标签", PartyTagname.class));

    /**
     * 身份证类型
     *
     * <p lang="en">
     * Person SID Type
     */
    /**/MenuNode personSidType = entry(SETTINGS, 2, "personSidType", simpleDictIndex("涉众身份类型", PartySidType.class));

    /**
     * 企业类型
     *
     * <p lang="en">
     * Organization Type
     */
    /**/MenuNode orgType = entry(SETTINGS, 3, "orgType", simpleDictIndex("组织机构类型", OrgType.class));

    /**
     * 联系信息分类
     *
     * <p lang="en">
     * Contact Category
     */
    /**/MenuNode contactCategory = entry(SETTINGS, 4, "contactCategory",
            simpleDictIndex("联系信息分组", ContactCategory.class));

    /**
     * 涉众记录分类
     *
     * <p lang="en">
     * Party Record Category
     */
    /**/MenuNode partyRecordCategory = mode(X_RECORDS) ? entry(SETTINGS, 5, "partyRecordCategory",
            simpleDictIndex("社会档案记录分类", PartyRecordCategory.class)) : null;

    /**
     * 企业组织(简)
     *
     * <p lang="en">
     * Organization/Person
     */
    MenuNode orgPersonAdmin = entry(PARENT, 20, "orgPersonAdmin", prefix.join("orgPerson/"));

    /**
     * 人员
     *
     * <p lang="en">
     * Person
     */
    MenuNode personAdmin = entry(PARENT, 30, "personAdmin", prefix.join("person/"));

    /**
     * 企业组织
     *
     * <p lang="en">
     * Organization
     */
    MenuNode orgAdmin = entry(PARENT, 40, "orgAdmin", prefix.join("org/"));

    /**
     * 部门
     *
     * <p lang="en">
     * Organization Unit
     */
    MenuNode orgUnitAdmin = entry(PARENT, 50, "orgUnitAdmin", prefix.join("orgUnit/"));

    /**
     * 雇员
     *
     * <p lang="en">
     * Employee
     */
    public MenuNode EMPLOYEE = menu(_frame_.HR, 25, "EMPLOYEE");

    /**
     * 雇员管理
     *
     * <p lang="en">
     * Employee Management
     */
    /**/MenuNode employeeAdmin = entry(EMPLOYEE, 1, "employeeAdmin", prefix.join("employee/"));

    /**
     * 雇员设置
     *
     * <p lang="en">
     * Employee Settings
     */
    /**/MenuNode EMPLOYEE_DICTS = menu(EMPLOYEE, 2, "EMPLOYEE_DICTS");

    /**
     * 职称
     *
     * <p lang="en">
     * Job Title
     */
    /*    */MenuNode jobTitle = entry(EMPLOYEE_DICTS, 1, "jobTitle", prefix.join("jobTitle/"));

    /**
     * 岗位
     *
     * <p lang="en">
     * Job Post
     */
    /*    */MenuNode jobPost = entry(EMPLOYEE_DICTS, 2, "jobPost", prefix.join("jobPost/"));

    /**
     * 学历
     *
     * <p lang="en">
     * Education Type
     */
    /*    */MenuNode educationType = entry(EMPLOYEE_DICTS, 3, "educationType", prefix.join("education/"));

    /**
     * 工作表现
     *
     * <p lang="en">
     * Job Performance
     */
    /*    */MenuNode jobPerformance = entry(EMPLOYEE_DICTS, 4, "jobPerformance", prefix.join("performance/"));

    /**
     * 职工技能
     *
     * <p lang="en">
     * Skill Category
     */
    /*    */MenuNode skillCategory = entry(EMPLOYEE_DICTS, 5, "skillCategory", prefix.join("skill/"));

}
