package com.bee32.sem.opportunity;

import com.bee32.plover.servlet.context.Location;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMOpportunityMenu
        extends MenuContribution {

    static Location CHANCE = WEB_APP.join("customer/chance/");
    static Location COMPETITOR = WEB_APP.join("customer/competitor/");

    @Contribution("crmcustomer")
    MenuEntry saleChanceManagement = new MenuEntry("saleChanceManagement");

    @Contribution("crmcustomer/saleChanceManagement")
    MenuEntry saleChance = new MenuEntry("saleChance", CHANCE.join("index.htm"));

    @Contribution("crmcustomer/saleChanceManagement")
    MenuEntry competitor = new MenuEntry("competitor", COMPETITOR.join("index.htm"));

    @Override
    protected void preamble() {
    }

}
