package com.bee32.sem.account;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMAccountReceMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMAccountModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 应收管理
     *
     * <p lang="en">
     */
    public MenuNode RECE = menu(_frame_.MAIN, 412, "RECE");

    /**
     * 应收初始化
     *
     * <p lang="en">
     */
    MenuNode receivableInitAdmin = entry(RECE, 1, "receivableInitAdmin", __.join("receivableInit/"));

    /**
     * 应收单管理
     *
     * <p lang="en">
     */
    MenuNode receivableAdmin = entry(RECE, 10, "receivableAdmin", __.join("receivable/"));

    /**
     * 收款单管理
     *
     * <p lang="en">
     */
    MenuNode receivedAdmin = entry(RECE, 20, "receivedAdmin", __.join("received/"));

    /**
     * 应收票据管理
     *
     * <p lang="en">
     */
    MenuNode noteReceivableAdmin = entry(RECE, 30, "noteReceivableAdmin", __.join("noteReceivable/"));

    /**
     * 应收核销
     *
     * <p lang="en">
     */
    MenuNode receivableVerification = entry(RECE, 40, "receivableVerification", __.join("receivableVerification/"));

    /**
     * 统计分析
     *
     * <p lang="en">
     */
    public MenuNode queryRece = menu(RECE, 50, "queryRece");

    /**
     * 应收余额
     *
     * <p lang="en">
     */
    /**/MenuNode balanceSheetRece = entry(queryRece, 1, "balanceSheetRece", __.join("balanceSheetRece/"));

    /**
     * 应收业务员余额
     *
     * <p lang="en">
     */
    /**/MenuNode balanceSheetSalesmanRece = entry(queryRece, 10, "balanceSheetSalesmanRece",
            __.join("balanceSheetSalesmanRece/"));

    /**
     * 应收部门余额
     *
     * <p lang="en">
     */
    /**/MenuNode balanceSheetOrgUnitRece = entry(queryRece, 20, "balanceSheetOrgUnitRece",
            __.join("balanceSheetOrgUnitRece/"));

    /**
     * 明细账
     *
     * <p lang="en">
     */
    /**/MenuNode detailRece = entry(queryRece, 30, "detailRece", __.join("detailRece/"));

    /**
     * 应收核销查询
     *
     * <p lang="en">
     */
    /**/MenuNode verificationQueryRece = entry(queryRece, 40, "verificationQueryRece",
            __.join("verificationQueryRece/"));

    /**
     *
     *
     * <p lang="en">
     */
// /**/MenuNode receivableAccountAge = entry(queryRece, 30, "receivableAccountAge",
// __.join("receivableAccountAge/"));

    /**
     *
     *
     * <p lang="en">
     */
// /**/MenuNode receivedAccountAge = entry(queryRece, 40, "receivedAccountAge",
// __.join("receivedAccountAge/"));

    /**
     *
     *
     * <p lang="en">
     */
// /**/MenuNode arrearageAnalysis = entry(queryRece, 50, "arrearageAnalysis",
// __.join("arrearageAnalysis/"));

    /**
     *
     *
     * <p lang="en">
     */
// /**/MenuNode gatheringPredict = entry(queryRece, 60, "gatheringPredict",
// __.join("gatheringPredict/"));

    /**
     * 未结算应收票据查询
     *
     * <p lang="en">
     */
    /**/MenuNode unbalancedNoteRece = entry(queryRece, 50, "unbalancedNoteRece", __.join("unbalancedNoteRece/"));

}
