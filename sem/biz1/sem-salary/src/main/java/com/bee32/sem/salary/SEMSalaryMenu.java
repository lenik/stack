package com.bee32.sem.salary;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 *
 *
 * <p lang="en">
 */
public class SEMSalaryMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(SEMSalaryModule.PREFIX_);

    public transient MenuNode HR = _frame_.HR;

    /**
     * 工资管理
     *
     * <p lang="en">
     */
    public MenuNode SALARY = menu(HR, 70, "WAGEADMIN");

    /**
     * 工资
     *
     * <p lang="en">
     */
    /**/MenuNode salary = entry(SALARY, 1, "salary", prefix.join("salary/"));

    /**
     * 奖惩事件
     *
     * <p lang="en">
     */
    /**/MenuNode eventBonus = entry(SALARY, 2, "eventBonus", prefix.join("eventBonus/"));

    /**
     * 工资字段定义
     *
     * <p lang="en">
     */
    /**/MenuNode salaryDef = entry(SALARY, 3, "salaryDef", prefix.join("salaryDef"));

    /**
     * 出勤管理
     *
     * <p lang="en">
     */
    public MenuNode ATTENDANCE = menu(HR, 71, "ATTENDANCE");

    /**
     * 月出勤记录
     *
     * <p lang="en">
     */
    /**/MenuNode attendancem = entry(ATTENDANCE, 1, "attendancem", prefix.join("a-month/"));

    /**
     * 日出勤记录
     *
     * <p lang="en">
     */
    /**/MenuNode attendanced = entry(ATTENDANCE, 2, "attendanced", prefix.join("a-day/"));

}
