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

    MenuNode accountInit = entry(ASSET, 10, "accountInit", __.join("init/"));

    MenuNode creditNoteListAdmin = entry(ASSET, 20, "creditNoteListAdmin", __.join("creditNote/list.jsf"));
    MenuNode creditNoteAdmin = entry(ASSET, 21, "creditNoteAdmin", __.join("creditNote/"));
    MenuNode paymentNoteListAdmin = entry(ASSET, 30, "paymentNoteListAdmin", __.join("paymentNote/list.jsf"));
    MenuNode paymentNoteAdmin = entry(ASSET, 31, "paymentNoteAdmin", __.join("paymentNote/"));
    MenuNode accountTicketListAdmin = entry(ASSET, 40, "accountTicketListAdmin", __.join("ticket/list.jsf"));
    MenuNode accountTicketAdmin = entry(ASSET, 41, "accountTicketAdmin", __.join("ticket/"));

    MenuNode assetQuery = entry(ASSET, 50, "assetQuery", __.join("query/"));

    public MenuNode CA = menu(ASSET, 60, "CA");   /*往来账 current account*/
    /**/public MenuNode balanceCategory = menu(CA, 1, "balanceCategory");
    /*    */MenuNode subjectBalance = entry(balanceCategory, 1, "subjectBalance", __.join("CA/subjectBalance/"));
    /*    */MenuNode balance = entry(balanceCategory, 2, "balance", __.join("CA/balance/"));
    /*    */MenuNode personBalance = entry(balanceCategory, 3, "personBalance", __.join("CA/personBalance/"));
    /*    */MenuNode orgUnitBalance = entry(balanceCategory, 4, "orgUnitBalance", __.join("CA/orgUnitBalance/"));
    /*    */MenuNode detail = entry(CA, 10, "detail", __.join("CA/detail/"));


    MenuNode balanceSheet = entry(ASSET, 70, "balanceSheet", __.join("balanceSheet/"));
    MenuNode profitSheet = entry(ASSET, 80, "profitSheet", __.join("profitSheet/"));






}
