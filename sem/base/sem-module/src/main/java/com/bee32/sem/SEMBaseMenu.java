package com.bee32.sem;

import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMBaseMenu
        extends MenuComposite {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    MenuNode security = entry(_frame_.SYSTEM, 50, "xrefs", JAVASCRIPT.join("showXrefs()"));

}
