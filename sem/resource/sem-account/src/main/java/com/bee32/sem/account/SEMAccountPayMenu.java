package com.bee32.sem.account;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAccountPayMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAccountModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode PAY = menu(_frame_.MAIN, 413, "PAY");

    MenuNode payableInitAdmin = entry(PAY, 1, "payableInitAdmin", __.join("payableInit/"));
    MenuNode payableAdmin = entry(PAY, 10, "payableAdmin", __.join("payable/"));
    MenuNode paidAdmin = entry(PAY, 20, "paidAdmin", __.join("paid/"));
    MenuNode notePayableAdmin = entry(PAY, 30, "notePayableAdmin", __.join("notePayable/"));
    MenuNode payableVerification = entry(PAY, 40, "payableVerification", __.join("payableVerification/"));

    public MenuNode queryPay = menu(PAY, 50, "queryPay");
    /**/MenuNode balanceSheetPay = entry(queryPay, 1, "balanceSheetPay", __.join("balanceSheetPay/"));
    /**/MenuNode detailPay = entry(queryPay, 10, "detailPay", __.join("detailPay/"));
    /**/MenuNode verificationQueryPay = entry(queryPay, 20, "verificationQueryPay", __.join("verificationQueryPay/"));

}
