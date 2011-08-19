package com.bee32.sem.purchase;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMPurchaseMenu
        extends MenuContribution {

    static Location PURCHASE_ = WEB_APP.join(SEMPurchaseModule.PREFIX_);

    public static MenuNode PURCHASE = menu(SEMFrameMenu.BIZ1, "purchase");

    static MenuNode orderAdmin = entry(PURCHASE, 1, "orderAdmin", PURCHASE_.join("purchase/index-rich.jsf"));

    @Override
    protected void preamble() {
    }

}
