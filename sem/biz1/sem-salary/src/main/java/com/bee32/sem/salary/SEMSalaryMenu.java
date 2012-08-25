package com.bee32.sem.salary;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMSalaryMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(SEMSalaryModule.PREFIX_);

    public transient MenuNode HR = _frame_.HR;

    public MenuNode SALARY = menu(HR, 70, "WAGEADMIN");
    /**/MenuNode salary = entry(SALARY, 1, "salary", prefix.join("salary/"));
    /**/MenuNode eventBonus = entry(SALARY, 2, "eventBonus", prefix.join("eventBonus/"));
    /**/MenuNode expression = entry(SALARY, 4, "expression", prefix.join("expr/"));
    /**/MenuNode salaryDef = entry(SALARY, 3, "salaryDef", prefix.join("salaryDef"));

    public MenuNode ATTENDANCE = menu(HR, 71, "ATTENDANCE");
    /**/MenuNode attendancem = entry(ATTENDANCE, 1, "attendancem", prefix.join("a-month/"));

}
