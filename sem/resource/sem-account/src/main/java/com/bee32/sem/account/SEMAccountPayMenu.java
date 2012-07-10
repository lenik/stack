package com.bee32.sem.account;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAccountPayMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAccountModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode PAY = menu(_frame_.MAIN, 460, "PAY");

    MenuNode payableAdmin = entry(PAY, 1, "payableAdmin", __.join("payable/"));

}
