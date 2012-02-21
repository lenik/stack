package com.bee32.sem.purchase;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMPurchaseMenu
        extends MenuContribution {

    static Location PURCHASE_ = WEB_APP.join(SEMPurchaseModule.PREFIX_);

    public static MenuNode PURCHASE = menu(SEMFrameMenu.BIZ1, 30, "purchase");

    static MenuNode makeOrderAdmin = entry(PURCHASE, 1, "makeOrderAdmin", PURCHASE_.join("make-order/"));
    static MenuNode makeTaskAdmin = entry(PURCHASE, 2, "makeTaskAdmin", PURCHASE_.join("make-task/"));
    static MenuNode materialPlanAdmin = entry(PURCHASE, 2, "materialPlanAdmin", PURCHASE_.join("material-plan/"));
    static MenuNode purchaseRequest = entry(PURCHASE, 2, "purchaseRequest", PURCHASE_.join("request/"));
    static MenuNode purchaseRequestItem = entry(PURCHASE, 2, "purchaseRequestItem", PURCHASE_.join("request-item/"));

    @Override
    protected void preamble() {
    }

}
