package com.bee32.sem.make;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMMakeMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMMakeModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    MenuNode makeStepNameAdmin = entry(_frame_.BIZ1, 200, "makeStepNameAdmin", __.join("makeStepName/"));
    MenuNode bomAdmin = entry(_frame_.BIZ1, 201, "bomAdmin", __.join("part/"));

}
