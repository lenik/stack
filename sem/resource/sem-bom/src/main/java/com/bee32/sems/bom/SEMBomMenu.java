package com.bee32.sems.bom;

import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMBomMenu
        extends MenuContribution {

    @Contribution(".")
    MenuEntry bom = new MenuEntry("bom");

        @Contribution("bom")
        MenuEntry bomadmin = new MenuEntry(1, "bomadmin", WEB_APP.join("bom/bomAdminJsf.htm"));

        @Contribution("bom")
        MenuEntry materialPriceStrategy = new MenuEntry(2, "materialPriceStrategy", WEB_APP.join("bom/materialPriceStrategy.htm"));

    @Override
    protected void preamble() {
    }

}
