package com.bee32.sem.uber;

import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMUberMenu
        extends MenuContribution {

    static MenuNode security = entry(SEMFrameMenu.EDIT, 50, "xrefs", //
            JAVASCRIPT.join("showXrefs()"));

}
