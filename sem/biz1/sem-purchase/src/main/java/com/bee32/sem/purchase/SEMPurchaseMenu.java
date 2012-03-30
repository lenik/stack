package com.bee32.sem.purchase;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.makebiz.SEMMakebizMenu;

public class SEMPurchaseMenu
        extends MenuContribution {

    static Location PREFIX_ = WEB_APP.join(SEMPurchaseModule.PREFIX_);

    public static transient MenuNode PURCHASE = SEMMakebizMenu.MAKEBIZ;

    static MenuNode purchaseRequest = entry(PURCHASE, 30, "purchaseRequest", PREFIX_.join("request/"));
    static MenuNode purchaseRequestItem = entry(PURCHASE, 40, "purchaseRequestItem", PREFIX_.join("request-item/"));

}
