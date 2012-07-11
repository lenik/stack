package com.bee32.sem.account;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAccountPayMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAccountModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode PAY = menu(_frame_.MAIN, 413, "PAY");

    MenuNode payableInitAdmin = entry(PAY, 1, "payableInitAdmin", __.join("payableInit/"));
    MenuNode payableAdmin = entry(PAY, 10, "payableAdmin", __.join("payable/"));
    MenuNode paiedAdmin = entry(PAY, 20, "paiedAdmin", __.join("paied/"));

}
