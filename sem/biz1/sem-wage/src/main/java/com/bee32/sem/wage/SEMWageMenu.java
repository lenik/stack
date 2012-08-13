package com.bee32.sem.wage;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMWageMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(SEMWageModule.PREFIX_);

    public transient MenuNode WAGE = _frame_.BIZ1;

    public MenuNode WAGEADMIN = menu(WAGE, 70, "WAGEADMIN");
    /**/MenuNode salary = entry(WAGEADMIN, 1, "salary", prefix.join("salary/"));
    /**/MenuNode bonus = entry(WAGEADMIN, 2, "bonus", prefix.join("bonus/"));
    /**/MenuNode expression = entry(WAGEADMIN, 4, "expression", prefix.join("expression/"));

    public MenuNode ATTENDANCE = menu(WAGE, 71, "ATTENDANCE");
    /**/MenuNode attendancem = entry(ATTENDANCE, 1, "attendancem", prefix.join("attendancem/"));

}
