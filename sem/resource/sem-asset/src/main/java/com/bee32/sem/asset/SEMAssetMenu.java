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
    /**/public MenuNode customerBalanceCategory = menu(CA, 1, "customerBalanceCategory");
    /*    */MenuNode customerSubjectBalance = entry(customerBalanceCategory, 1, "customerSubjectBalance", __.join("customerSubjectBalance/"));
    /*    */MenuNode customerBalance = entry(customerBalanceCategory, 2, "customerBalance", __.join("customerBalance/"));
    /*    */MenuNode customerSalesmanBalance = entry(customerBalanceCategory, 3, "customerSalesmanBalance", __.join("customerSalesmanBalance/"));
    /*    */MenuNode customerOrgUnitBalance = entry(customerBalanceCategory, 4, "customerOrgUnitBalance", __.join("customerOrgUnitBalance/"));
    /**/public MenuNode customerDetailCategory = menu(CA, 10, "customerDetailCategory");
    /*    */MenuNode customerSubjectDetail = entry(customerDetailCategory, 1, "customerSubjectDetail", __.join("customerSubjectDetail/"));
    /*    */MenuNode customerDetail = entry(customerDetailCategory, 2, "customerDetail", __.join("customerDetail/"));
    /*    */MenuNode customerSalesmanDetail = entry(customerDetailCategory, 3, "customerSalesmanDetail", __.join("customerSalesmanDetail/"));
    /*    */MenuNode customerOrgUnitDetail = entry(customerDetailCategory, 4, "customerOrgUnitDetail", __.join("customerOrgUnitDetail/"));
    /**/public MenuNode supplierBalanceCategory = menu(CA, 20, "supplierBalanceCategory");
    /*    */MenuNode supplierSubjectBalance = entry(supplierBalanceCategory, 1, "supplierSubjectBalance", __.join("supplierSubjectBalance/"));
    /*    */MenuNode supplierBalance = entry(supplierBalanceCategory, 2, "supplierBalance", __.join("supplierBalance/"));
    /*    */MenuNode supplierSalesmanBalance = entry(supplierBalanceCategory, 3, "supplierSalesmanBalance", __.join("supplierSalesmanBalance/"));
    /*    */MenuNode supplierOrgUnitBalance = entry(supplierBalanceCategory, 4, "supplierOrgUnitBalance", __.join("supplierOrgUnitBalance/"));
    /**/public MenuNode supplierDetailCategory = menu(CA, 30, "supplierDetailCategory");
    /*    */MenuNode supplierSubjectDetail = entry(supplierDetailCategory, 1, "supplierSubjectDetail", __.join("supplierSubjectDetail/"));
    /*    */MenuNode supplierDetail = entry(supplierDetailCategory, 2, "supplierDetail", __.join("supplierDetail/"));
    /*    */MenuNode supplierSalesmanDetail = entry(supplierDetailCategory, 3, "supplierSalesmanDetail", __.join("supplierSalesmanDetail/"));
    /*    */MenuNode supplierOrgUnitDetail = entry(supplierDetailCategory, 4, "supplierOrgUnitDetail", __.join("supplierOrgUnitDetail/"));







}
