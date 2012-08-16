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

    public transient MenuNode PARENT = _frame_.BIZ1;

    public MenuNode SALARY = menu(PARENT, 70, "WAGEADMIN");
    /**/MenuNode salary = entry(SALARY, 1, "salary", prefix.join("salary/"));
    /**/MenuNode bonus = entry(SALARY, 2, "bonus", prefix.join("bonus/"));
    /**/MenuNode expression = entry(SALARY, 4, "expression", prefix.join("expr/"));

    public MenuNode ATTENDANCE = menu(PARENT, 71, "ATTENDANCE");
    /**/MenuNode attendancem = entry(ATTENDANCE, 1, "attendancem", prefix.join("a-month/"));

}
