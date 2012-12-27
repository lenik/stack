package com.bee32.sem.make;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.material.SEMMaterialMenu;

public class SEMMakeMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMMakeModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    SEMMaterialMenu _material_ = require(SEMMaterialMenu.class);

    MenuNode makeStepNameAdmin = entry(_material_.MATERIAL, 30, "makeStepNameAdmin", __.join("makeStepName/"));
    MenuNode bomAdmin = entry(_material_.MATERIAL, 40, "bomAdmin", __.join("part/"));

}
