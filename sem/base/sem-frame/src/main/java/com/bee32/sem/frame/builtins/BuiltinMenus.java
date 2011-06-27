package com.bee32.sem.frame.builtins;

import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.frame.menu.SEMFrameMenu;

public class BuiltinMenus
        extends MenuContribution {

    static String NAME = "SEM-Frame 0.2";

    MenuNode aboutFrame = entry(SEMFrameMenu.HELP, "aboutFrame", JAVASCRIPT.join("alert('" + NAME + "')"));

    @Override
    protected void preamble() {
    }

}
