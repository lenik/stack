package com.bee32.sem.account;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 * 应付
 *
 * <p lang="en">
 */
public class SEMAccountPayMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAccountModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 应付管理
     *
     * <p lang="en">
     */
    public MenuNode PAY = menu(_frame_.MAIN, 413, "PAY");

    /**
     * 应付初始化
     *
     * <p lang="en">
     */
    MenuNode payableInitAdmin = entry(PAY, 1, "payableInitAdmin", __.join("payableInit/"));

    /**
     * 应付单管理
     *
     * <p lang="en">
     */
    MenuNode payableAdmin = entry(PAY, 10, "payableAdmin", __.join("payable/"));

    /**
     * 付款单管理
     *
     * <p lang="en">
     */
    MenuNode paidAdmin = entry(PAY, 20, "paidAdmin", __.join("paid/"));

    /**
     * 应付票据管理
     *
     * <p lang="en">
     */
    MenuNode notePayableAdmin = entry(PAY, 30, "notePayableAdmin", __.join("notePayable/"));

    /**
     * 应付核销
     *
     * <p lang="en">
     */
    MenuNode payableVerification = entry(PAY, 40, "payableVerification", __.join("payableVerification/"));

    /**
     * 统计分析
     *
     * <p lang="en">
     */
    public MenuNode queryPay = menu(PAY, 50, "queryPay");

    /**
     * 应付余额
     *
     * <p lang="en">
     */
    /**/MenuNode balanceSheetPay = entry(queryPay, 1, "balanceSheetPay", __.join("balanceSheetPay/"));

    /**
     * 应付采购员余额
     *
     * <p lang="en">
     */
    /**/MenuNode balanceSheetSalesmanPay = entry(queryPay, 10, "balanceSheetSalesmanPay",
            __.join("balanceSheetSalesmanPay/"));

    /**
     * 应付部门余额
     *
     * <p lang="en">
     */
    /**/MenuNode balanceSheetOrgUnitPay = entry(queryPay, 20, "balanceSheetOrgUnitPay",
            __.join("balanceSheetOrgUnitPay/"));

    /**
     * 明细账
     *
     * <p lang="en">
     */
    /**/MenuNode detailPay = entry(queryPay, 30, "detailPay", __.join("detailPay/"));

    /**
     * 应付核销查询
     *
     * <p lang="en">
     */
    /**/MenuNode verificationQueryPay = entry(queryPay, 40, "verificationQueryPay", __.join("verificationQueryPay/"));

    /**
     * 未结算应付票据查询
     *
     * <p lang="en">
     */
    /**/MenuNode unbalancedNotePay = entry(queryPay, 50, "unbalancedNotePay", __.join("unbalancedNotePay/"));

}
