package com.bee32.sem.makebiz;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMMakebizMenu
        extends MenuContribution {

    static Location PREFIX_ = WEB_APP.join(SEMMakebizModule.PREFIX_);

    public static MenuNode MAKEBIZ = menu(SEMFrameMenu.MAIN, 450, "makeBiz");

    static MenuNode makeOrderAdmin = entry(MAKEBIZ, 1, "makeOrderAdmin", PREFIX_.join("order/"));
    static MenuNode makeTaskAdmin = entry(MAKEBIZ, 10, "makeTaskAdmin", PREFIX_.join("task/"));
    static MenuNode materialPlanAdmin = entry(MAKEBIZ, 20, "materialPlanAdmin", PREFIX_.join("plan/"));
    static MenuNode deliveryNoteAdmin = entry(MAKEBIZ, 50, "deliveryNoteAdmin", PREFIX_.join("delivery/"));
    static MenuNode makeProcessAdmin = entry(MAKEBIZ, 60, "makeProcessAdmin", PREFIX_.join("process/"));

}
