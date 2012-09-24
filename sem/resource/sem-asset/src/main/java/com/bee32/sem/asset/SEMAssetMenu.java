package com.bee32.sem.asset;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAssetMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAssetModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode ASSET = menu(_frame_.MAIN, 410, "ASSET");

    MenuNode accountSubjectAdmin = entry(ASSET, 1, "accountSubjectAdmin", __.join("subject/"));

    MenuNode accountInit = entry(ASSET, 2, "accountInit", __.join("init/"));

    MenuNode creditNoteAdmin = entry(ASSET, 3, "creditNoteAdmin", __.join("creditNote/"));
    MenuNode paymentNoteAdmin = entry(ASSET, 4, "paymentNoteAdmin", __.join("paymentNote/"));
    MenuNode accountTicketAdmin = entry(ASSET, 5, "accountTicketAdmin", __.join("ticket/"));

    MenuNode assetQuery = entry(ASSET, 7, "assetQuery", __.join("query/"));

    public MenuNode CA = menu(ASSET, 100, "CA");   /*往来账 current account*/
    /**/public MenuNode balanceCategory = menu(CA, 1, "balanceCategory");
    /*    */MenuNode subjectBalance = entry(balanceCategory, 1, "subjectBalance", __.join("CA/subjectBalance/"));
    /*    */MenuNode balance = entry(balanceCategory, 2, "balance", __.join("CA/balance/"));
    /*    */MenuNode personBalance = entry(balanceCategory, 3, "personBalance", __.join("CA/personBalance/"));
    /*    */MenuNode orgUnitBalance = entry(balanceCategory, 4, "orgUnitBalance", __.join("CA/orgUnitBalance/"));
    /**/public MenuNode detailCategory = menu(CA, 10, "detailCategory");
    /*    */MenuNode subjectDetail = entry(detailCategory, 1, "subjectDetail", __.join("CA/subjectDetail/"));
    /*    */MenuNode detail = entry(detailCategory, 2, "detail", __.join("CA/detail/"));
    /*    */MenuNode personDetail = entry(detailCategory, 3, "personDetail", __.join("CA/personDetail/"));
    /*    */MenuNode orgUnitDetail = entry(detailCategory, 4, "orgUnitDetail", __.join("CA/orgUnitDetail/"));








}
