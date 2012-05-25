package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMProcessMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMProcessModule.PREFIX + "/");
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode CORE = menu(_frame_.PROCESS, 10, "process");
    public MenuNode VERIFY_POLICY = menu(CORE, 10, "verifyPolicy");

    MenuNode PREFERENCE = entry(CORE, 20, "preference", __.join("pref/index-rich.jsf"));

    MenuNode LIST = entry(VERIFY_POLICY, 1, "list", __.join("v1/index-rich.jsf"));
    MenuNode LEVEL = entry(VERIFY_POLICY, 2, "level", __.join("v1x/index-rich.jsf"));

    // MenuNode p2next = entry(verifyPolicy, 3, "p2next", PROCESS.join("p2next/index.do"));

}
