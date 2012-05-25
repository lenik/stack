package com.bee32.sem.make;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.SEMInventoryMenu;

public class SEMMakeMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMMakeModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    SEMInventoryMenu _inventory_ = require(SEMInventoryMenu.class);

    MenuNode makeStepNameAdmin = entry(_inventory_.MATERIAL, 30, "makeStepNameAdmin", __.join("makeStepName/"));
    MenuNode bomAdmin = entry(_inventory_.MATERIAL, 40, "bomAdmin", __.join("part/"));

}
