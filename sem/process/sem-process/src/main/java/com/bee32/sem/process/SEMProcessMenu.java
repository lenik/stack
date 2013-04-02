package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMProcessMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMProcessModule.PREFIX + "/");
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode VERIFY_POLICY = menu(_frame_.SYSTEM, 700, "VERIFY_POLICY");

    MenuNode preference = entry(_frame_.SYSTEM, 800, "preference", __.join("pref"));
    MenuNode list = entry(VERIFY_POLICY, 1, "list", __.join("v1"));
    MenuNode level = entry(VERIFY_POLICY, 2, "level", __.join("v1x"));
    // MenuNode p2next = entry(verifyPolicy, 3, "p2next", __.join("p2next/index.do"));

}
