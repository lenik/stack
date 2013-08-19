package com.bee32.sem.inventory;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.material.SEMMaterialModule;
import com.bee32.sem.process.SEMProcessMenu;

/**
 * 库存管理菜单
 *
 * <p lang="en">
 * SEM Inventory Menu
 */
public class SEMInventoryMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    static Location __ = WEB_APP.join(SEMInventoryModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    SEMProcessMenu _process_ = require(SEMProcessMenu.class);

    static Location _stock(StockOrderSubject subject) {
        return __.join("stock/?subject=" + subject.getValue());
    }

    static Location _material_ = WEB_APP.join(SEMMaterialModule.PREFIX_);

    /**
     * 库存管理
     *
     * <p lang="en">
     */
    public MenuNode INVENTORY = menu(_frame_.MAIN, 440, "INVENTORY");

    /**
     * 库存初始化
     *
     * <p lang="en">
     */
    /*    */MenuNode init = entry(INVENTORY, 10, "init", __.join("stock/INIT/"));

    /**
     * 采购入库
     *
     * <p lang="en">
     */
    /*    */MenuNode takeIn = entry(INVENTORY, 20, "takeIn", _stock(StockOrderSubject.TAKE_IN));

    /**
     * 销售出库
     *
     * <p lang="en">
     */
    /*    */MenuNode takeOut = entry(INVENTORY, 21, "takeOut", _stock(StockOrderSubject.TAKE_OUT));

    /**
     * 计划出库
     *
     * <p lang="en">
     */
    /*    */MenuNode planOut = entry(INVENTORY, 42, "planOut", _stock(StockOrderSubject.PLAN_OUT));

    /**
     * 库存盘点
     *
     * <p lang="en">
     */
    /*    */MenuNode stocktaking = entry(INVENTORY, 60, "stocktaking", __.join("stock/STKD/"));

    /**
     * 库存调拨-拨出
     *
     * <p lang="en">
     */
    /*    */MenuNode transferOut = entry(INVENTORY, 70, "transferOut", __.join("stock/XFER_OUT/"));

    /**
     * 库存调拨-调入
     *
     * <p lang="en">
     */
    /*    */MenuNode transferIn = entry(INVENTORY, 71, "transferIn", __.join("stock/XFER_IN/"));

    /**
     * 委外出库
     *
     * <p lang="en">
     */
    /*    */MenuNode outsourcingOut = entry(INVENTORY, 80, "outsourcingOut", __.join("stock/OSP_OUT/"));

    /**
     * 委外入库
     *
     * <p lang="en">
     */
    /*    */MenuNode outsourcingIn = entry(INVENTORY, 81, "outsourcingIn", __.join("stock/OSP_IN/"));

    /*    */MenuNode __1 = _separator_(INVENTORY, 100);

    /**
     * 库存查询(含明细)
     *
     * <p lang="en">
     */
    /*    */MenuNode stockQuery = entry(INVENTORY, 110, "stockQuery", __.join("query/"));

    /**
     * 不合格率查询
     *
     * <p lang="en">
     */
    /*    */MenuNode unqualifiedQuery = entry(INVENTORY, 120, "unqualifiedQuery", __.join("unqualifiedQuery/"));

    /**
     * 门店利润查询
     *
     * <p lang="en">
     */
    /*    */MenuNode profitQuery = entry(INVENTORY, 130, "profitQuery", __.join("profitQuery/"));

    /*    */MenuNode __2 = _separator_(INVENTORY, 999);

    /**
     * 库存参数设置
     *
     * <p lang="en">
     */
    public MenuNode SETTINGS = menu(INVENTORY, 1000, "SETTINGS");

    /**
     * ${tr.inventory.warehouse}设置
     *
     * <p lang="en">
     */
    /*    */MenuNode stockWarehouse = entry(SETTINGS, 1, "stockWarehouse", _material_.join("warehouse/"));

    /**
     * 库位管理
     *
     * <p lang="en">
     */
    /*    */MenuNode stockLocation = entry(SETTINGS, 10, "stockLocation", _material_.join("location/"));

    /**
     * 库存审核策略
     *
     * <p lang="en">
     */
    /*    */MenuNode verifyPolicy = entry(_process_.VERIFY_POLICY, 100, "verifyPolicy", __.join("verify/"));

}
