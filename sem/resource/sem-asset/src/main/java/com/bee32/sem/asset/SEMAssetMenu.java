package com.bee32.sem.asset;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 * 资产管理菜单
 *
 * <p lang="en">
 * Asset Management Menu
 */
public class SEMAssetMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAssetModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 资产管理
     *
     * <p lang="en">
     * Asset Mgmt
     */
    public MenuNode ASSET = menu(_frame_.MAIN, 410, "ASSET");

    /**
     * 会计科目管理
     *
     * <p lang="en">
     * Account Subject
     */
    MenuNode accountSubjectAdmin = entry(ASSET, 1, "accountSubjectAdmin", __.join("subject/"));

    /**
     * 资产初始化
     *
     * <p lang="en">
     * Account Init
     */
    MenuNode accountInit = entry(ASSET, 10, "accountInit", __.join("init/"));

    /**
     * 收款单管理
     *
     * <p lang="en">
     * Credit Note
     */
    MenuNode creditNoteAdmin = entry(ASSET, 21, "creditNoteAdmin", __.join("creditNote/"));

    /**
     * 付款单管理
     *
     * <p lang="en">
     * Payment Note
     */
    MenuNode paymentNoteAdmin = entry(ASSET, 31, "paymentNoteAdmin", __.join("paymentNote/"));

    /**
     * 凭证管理
     *
     * <p lang="en">
     * Account Ticket
     */
    MenuNode accountTicketAdmin = entry(ASSET, 41, "accountTicketAdmin", __.join("ticket/"));

    /**
     * 资产查询
     *
     * <p lang="en">
     */
    MenuNode assetQuery = entry(ASSET, 50, "assetQuery", __.join("query/"));

    /**
     * 往来
     *
     * <p lang="en">
     */
    public MenuNode CA = menu(ASSET, 60, "CA"); /* 往来账 current account */

    /**
     * 余额
     *
     * <p lang="en">
     */
    /**/public MenuNode balanceCategory = menu(CA, 1, "balanceCategory");

    /**
     * 科目余额
     *
     * <p lang="en">
     */
    /*    */MenuNode subjectBalance = entry(balanceCategory, 1, "subjectBalance", __.join("CA/subjectBalance/"));

    /**
     * 余额
     *
     * <p lang="en">
     */
    /*    */MenuNode balance = entry(balanceCategory, 2, "balance", __.join("CA/balance/"));

    /**
     * 经办人余额
     *
     * <p lang="en">
     */
    /*    */MenuNode personBalance = entry(balanceCategory, 3, "personBalance", __.join("CA/personBalance/"));

    /**
     * 部门余额
     *
     * <p lang="en">
     */
    /*    */MenuNode orgUnitBalance = entry(balanceCategory, 4, "orgUnitBalance", __.join("CA/orgUnitBalance/"));

    /**
     * 明细
     *
     * <p lang="en">
     */
    /*    */MenuNode detail = entry(CA, 10, "detail", __.join("CA/detail/"));

    /**
     * 资产负债表
     *
     * <p lang="en">
     */
    MenuNode balanceSheet = entry(ASSET, 70, "balanceSheet", __.join("balanceSheet/"));

    /**
     * 利润表
     *
     * <p lang="en">
     */
    MenuNode profitSheet = entry(ASSET, 80, "profitSheet", __.join("profitSheet/"));

}
