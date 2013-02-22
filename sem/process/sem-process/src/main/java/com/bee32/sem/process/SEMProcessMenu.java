package com.bee32.sem.process;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMProcessMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMProcessModule.PREFIX + "/");
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode WORKFLOW = menu(_frame_.START, 30, "WORKFLOW");
    MenuNode my = entry(WORKFLOW, 10, "my", __.join("task/my"));
    MenuNode inbox = entry(WORKFLOW, 20, "inbox", __.join("task/inbox"));
    MenuNode queued = entry(WORKFLOW, 30, "queued", __.join("task/queued"));
    MenuNode archived = entry(WORKFLOW, 40, "archived", __.join("task/archived"));
    // MenuNode __1 = _separator_(WORKFLOW, 100);
    // MenuNode processModel= entry(WORKFLOW, 110, "processModel", __.join("process/model"));

    public MenuNode CORE = menu(_frame_.PROCESS, 10, "CORE");
    public MenuNode VERIFY_POLICY = menu(CORE, 10, "VERIFY_POLICY");

    MenuNode preference = entry(CORE, 20, "preference", __.join("pref"));
    MenuNode list = entry(VERIFY_POLICY, 1, "list", __.join("v1"));
    MenuNode level = entry(VERIFY_POLICY, 2, "level", __.join("v1x"));
    // MenuNode p2next = entry(verifyPolicy, 3, "p2next", __.join("p2next/index.do"));

}
