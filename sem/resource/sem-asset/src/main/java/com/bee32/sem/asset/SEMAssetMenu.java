package com.bee32.sem.asset;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAssetMenu
        extends MenuContribution {

    static Location ASSET_ = WEB_APP.join(SEMAssetModule.PREFIX_);

    public static MenuNode ASSET = menu(SEMFrameMenu.BIZ1, "asset");

    static MenuNode accountTitleAdmin = entry(ASSET, 1, "accountSubjectAdmin", ASSET_.join("accountSubject/"));
    static MenuNode budgetRequestAdmin = entry(ASSET, 1, "budgetRequestAdmin", ASSET_.join("budgetRequest/"));
    static MenuNode accountTicketAdmin = entry(ASSET, 1, "accountTicketAdmin", ASSET_.join("accountTicket/"));

    static MenuNode stockSaleAdmin = entry(ASSET, 1, "stockSaleAdmin", ASSET_.join("stockTrade/?type=SALE"));
    static MenuNode stockPurchaseAdmin = entry(ASSET, 1, "stockPurchaseAdmin", ASSET_.join("stockTrade/?type=PURCHASE"));

    @Override
    protected void preamble() {
    }
}
