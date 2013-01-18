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

    MenuNode makeOrderListAdmin = entry(MAKEBIZ, 1, "makeOrderListAdmin", __.join("order/list.jsf"));
    MenuNode makeOrderAdmin = entry(MAKEBIZ, 2, "makeOrderAdmin", __.join("order/"));

    MenuNode makeTaskListAdmin = entry(MAKEBIZ, 10, "makeTaskListAdmin", __.join("task/list.jsf"));
    MenuNode makeTaskAdmin = entry(MAKEBIZ, 11, "makeTaskAdmin", __.join("task/"));

    MenuNode materialPlanAdmin = entry(MAKEBIZ, 20, "materialPlanAdmin", __.join("plan/"));

    MenuNode deliveryNoteListAdmin = entry(MAKEBIZ, 50, "deliveryNoteListAdmin", __.join("delivery/list.jsf"));
    MenuNode deliveryNoteAdmin = entry(MAKEBIZ, 51, "deliveryNoteAdmin", __.join("delivery/"));


    MenuNode makeProcessListAdmin = entry(MAKEBIZ, 60, "makeProcessListAdmin", __.join("process/list.jsf"));
    MenuNode makeProcessAdmin = entry(MAKEBIZ, 61, "makeProcessAdmin", __.join("process/"));

    MenuNode makeStepItemListAdmin = entry(MAKEBIZ, 70, "makeStepItemListAdmin", __.join("stepItem/list.jsf"));

}
