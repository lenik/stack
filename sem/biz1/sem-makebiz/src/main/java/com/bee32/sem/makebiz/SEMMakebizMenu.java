package com.bee32.sem.makebiz;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.chance.SEMChanceMenu;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.SEMInventoryMenu;
import com.bee32.sem.inventory.SEMInventoryModule;

/**
 *
 *
 * <p lang="en">
 */
public class SEMMakebizMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMMakebizModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    SEMInventoryMenu _invMenu = require(SEMInventoryMenu.class);
    SEMChanceMenu _saleMenu = require(SEMChanceMenu.class);

    /**
     * 采购
     *
     * <p lang="en">
     */
    public MenuNode PURCHASE = menu(_frame_.MAIN, 500, "PURCHASE");

    /**
     * 生产
     *
     * <p lang="en">
     */
    public MenuNode PRODUCTION = menu(_frame_.MAIN, 600, "PRODUCTION");

    /**
     * 生产订单
     *
     * <p lang="en">
     */
    /**/MenuNode makeOrderAdmin = entry(_saleMenu.SALEMGMT, 20, "makeOrderAdmin", __.join("order/"));

    /**
     * 生产订单List
     *
     * <p lang="en">
     */
    /**/MenuNode makeOrderList = entry(_saleMenu.SALEMGMT, 19, "makeOrderList", __.join("order/list.jsf"));

    /**
     * 生产任务
     *
     * <p lang="en">
     */
    /**/MenuNode makeTaskAdmin = entry(PRODUCTION, 11, "makeTaskAdmin", __.join("task/"));

    /**
     * 物料计划
     *
     * <p lang="en">
     */
    /**/MenuNode materialPlanAdmin = entry(PURCHASE, 20, "materialPlanAdmin", __.join("plan/"));

    /**
     * 送货单
     *
     * <p lang="en">
     */
    /**/MenuNode deliveryNoteAdmin = entry(_saleMenu.SALEMGMT, 51, "deliveryNoteAdmin", __.join("delivery/"));

    /**
     * 工艺流转单
     *
     * <p lang="en">
     */
    /**/MenuNode makeProcessAdmin = entry(PRODUCTION, 61, "makeProcessAdmin", __.join("process/"));

    /**
     * 生产数据
     *
     * <p lang="en">
     */
    /**/MenuNode makeStepItemAdmin = entry(PRODUCTION, 70, "makeStepItemAdmin", __.join("stepItem/"));

    /**
     * 材料表导入
     *
     * <p lang="en">
     */
    /**/MenuNode batchImport = entry(PRODUCTION, 80, "batchImport", __.join("import/"));

    /**
     * 生产进度查询
     *
     * <p lang="en">
     */
    /**/MenuNode productionSchedule = entry(PRODUCTION, 90, "productionSchedule", __.join("yield/"));

    static Location __Inv = WEB_APP.join(SEMInventoryModule.PREFIX_);

    /**
     * 生产入库
     *
     * <p lang="en">
     */
    /**/MenuNode factoryTakeIn = entry(_invMenu.INVENTORY, 90, "factoryTakeIn", __Inv.join("stock/FACTORY_IN/"));

    /**
     * 生产出库
     *
     * <p lang="en">
     */
    /**/MenuNode factoryTakeOut = entry(_invMenu.INVENTORY, 91, "factoryTakeOut", __Inv.join("stock/FACTORY_OUT/"));

}
