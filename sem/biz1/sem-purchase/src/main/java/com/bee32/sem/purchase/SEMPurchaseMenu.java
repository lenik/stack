package com.bee32.sem.purchase;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMPurchaseMenu
        extends MenuContribution {

    static Location PURCHASE_ = WEB_APP.join(SEMPurchaseModule.PREFIX_);

    public static MenuNode PURCHASE = menu(SEMFrameMenu.BIZ1, "purchase");

    static MenuNode makeOrderAdmin = entry(PURCHASE, 1, "makeOrderAdmin", PURCHASE_.join("makeOrder/index-rich.jsf"));

    @Override
    protected void preamble() {
    }

}
