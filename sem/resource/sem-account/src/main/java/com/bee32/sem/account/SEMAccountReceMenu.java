package com.bee32.sem.account;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAccountReceMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAccountModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode RECE = menu(_frame_.MAIN, 412, "RECE");

    MenuNode receivableInitAdmin = entry(RECE, 1, "receivableInitAdmin", __.join("receivableInit/"));
    MenuNode receivableAdmin = entry(RECE, 10, "receivableAdmin", __.join("receivable/"));
    MenuNode receivedAdmin = entry(RECE, 20, "receivedAdmin", __.join("received/"));
    MenuNode noteReceivableAdmin = entry(RECE, 30, "noteReceivableAdmin", __.join("noteReceivable/"));
    MenuNode receivableVerification = entry(RECE, 40, "receivableVerification", __.join("receivableVerification/"));


    public MenuNode queryRece = menu(RECE, 50, "queryRece");
    /**/MenuNode balanceSheetRece = entry(queryRece, 1, "balanceSheetRece", __.join("balanceSheetRece/"));
    /**/MenuNode balanceSheetSalesmanRece = entry(queryRece, 10, "balanceSheetSalesmanRece", __.join("balanceSheetSalesmanRece/"));
    /**/MenuNode balanceSheetOrgUnitRece = entry(queryRece, 20, "balanceSheetOrgUnitRece", __.join("balanceSheetOrgUnitRece/"));
    /**/MenuNode detailRece = entry(queryRece, 30, "detailRece", __.join("detailRece/"));
    /**/MenuNode verificationQueryRece = entry(queryRece, 40, "verificationQueryRece", __.join("verificationQueryRece/"));
//    /**/MenuNode receivableAccountAge = entry(queryRece, 30, "receivableAccountAge", __.join("receivableAccountAge/"));
//    /**/MenuNode receivedAccountAge = entry(queryRece, 40, "receivedAccountAge", __.join("receivedAccountAge/"));
//    /**/MenuNode arrearageAnalysis = entry(queryRece, 50, "arrearageAnalysis", __.join("arrearageAnalysis/"));
//    /**/MenuNode gatheringPredict = entry(queryRece, 60, "gatheringPredict", __.join("gatheringPredict/"));
}
