package com.bee32.sem.purchase;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.makebiz.SEMMakebizMenu;

/**
 *
 *
 * <p lang="en">
 */
public class SEMPurchaseMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMPurchaseModule.PREFIX_);
    SEMMakebizMenu _makebiz_ = require(SEMMakebizMenu.class);

    public transient MenuNode PURCHASE = _makebiz_.PURCHASE;

    /**
     * 采购请求管理
     *
     * <p lang="en">
     * Purchase Request
     */
    MenuNode purchaseRequest = entry(PURCHASE, 30, "purchaseRequest", __.join("request/"));

    /**
     * 询价
     *
     * <p lang="en">
     * Purchase Request Item
     */
    MenuNode purchaseRequestItem = entry(PURCHASE, 40, "purchaseRequestItem", __.join("request-item/"));

}
