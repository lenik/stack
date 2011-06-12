package com.bee32.sem.chance;

import com.bee32.plover.servlet.context.Location;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMChanceMenu
        extends MenuContribution {

    static Location CHANCE = WEB_APP.join("customer/chance/");
    static Location COMPETITOR = WEB_APP.join("customer/competitor/");

    @Contribution("crmcustomer")
    MenuEntry saleChanceManagement = new MenuEntry("saleChanceManagement");

    @Contribution("crmcustomer/saleChanceManagement")
    MenuEntry saleChance = new MenuEntry("saleChance", CHANCE.join("index.do"));

    @Contribution("crmcustomer/saleChanceManagement")
    MenuEntry competitor = new MenuEntry("competitor", COMPETITOR.join("index.do"));

    @Override
    protected void preamble() {
    }

}
