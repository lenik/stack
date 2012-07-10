package com.bee32.sem.account;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAccountReceMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAccountModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode RECE = menu(_frame_.MAIN, 450, "RECE");

    MenuNode receivableInitAdmin = entry(RECE, 1, "receivableInitAdmin", __.join("receivableInit/"));
    MenuNode receivableAdmin = entry(RECE, 10, "receivableAdmin", __.join("receivable/"));



}
