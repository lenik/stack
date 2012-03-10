package com.bee32.sem.make;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMMakeMenu
        extends MenuContribution {

    static Location PREFIX_ = WEB_APP.join(SEMMakeModule.PREFIX_);

    public static MenuNode MAKE = menu(SEMFrameMenu.MAIN, 450, "make");

    static MenuNode makeOrderAdmin = entry(MAKE, 1, "makeOrderAdmin", PREFIX_.join("order/"));
    static MenuNode makeTaskAdmin = entry(MAKE, 10, "makeTaskAdmin", PREFIX_.join("task/"));
    static MenuNode materialPlanAdmin = entry(MAKE, 20, "materialPlanAdmin", PREFIX_.join("plan/"));
    static MenuNode deliveryNoteAdmin = entry(MAKE, 50, "deliveryNoteAdmin", PREFIX_.join("delivery/"));

}
