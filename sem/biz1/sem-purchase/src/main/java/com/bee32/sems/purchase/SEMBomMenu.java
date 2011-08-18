package com.bee32.sems.purchase;

public class SEMPurchaseMenu
        extends MenuContribution {


    public static MenuNode PURCHASE = menu(SEMFrameMenu.BASE, "purchase");
        static MenuNode orderAdmin = entry(PURCHASE, 1, "orderAdmin", PURCHASE_.join("purchase/index-rich.jsf"));

    @Override
    protected void preamble() {
    }

}
