package com.bee32.sem.frame.builtins;

import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.action.Action;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuItem;

public class BuiltinMenus
        extends MenuContribution {

    static String NAME = "SEM-Frame 0.2";

    @Contribution("help")
    MenuItem aboutFrame = new MenuItem("aboutFrame", new Action(JAVASCRIPT.join("alert('" + NAME + "')")));

    @Override
    protected void preamble() {
    }

}
