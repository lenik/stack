package com.bee32.sem.makebiz;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMMakebizMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMMakebizModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode MAKEBIZ = menu(_frame_.MAIN, 450, "MAKEBIZ");

    MenuNode makeOrderAdmin = entry(MAKEBIZ, 1, "makeOrderAdmin", __.join("order/"));
    MenuNode makeOrderListAdmin = entry(MAKEBIZ, 1, "makeOrderListAdmin", __.join("order/list.jsf"));
    MenuNode makeTaskAdmin = entry(MAKEBIZ, 10, "makeTaskAdmin", __.join("task/"));
    MenuNode makeTaskListAdmin = entry(MAKEBIZ, 11, "makeTaskListAdmin", __.join("task/list.jsf"));
    MenuNode materialPlanAdmin = entry(MAKEBIZ, 20, "materialPlanAdmin", __.join("plan/"));
    MenuNode deliveryNoteAdmin = entry(MAKEBIZ, 50, "deliveryNoteAdmin", __.join("delivery/"));
    MenuNode makeProcessAdmin = entry(MAKEBIZ, 60, "makeProcessAdmin", __.join("process/"));

}
