package com.bee32.sem.makebiz;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.chance.SEMChanceMenu;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.SEMInventoryMenu;
import com.bee32.sem.inventory.SEMInventoryModule;

public class SEMMakebizMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMMakebizModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    SEMInventoryMenu _invMenu = require(SEMInventoryMenu.class);
    SEMChanceMenu _saleMenu = require(SEMChanceMenu.class);

    public MenuNode PURCHASE = menu(_frame_.MAIN, 500, "PURCHASE");

    public MenuNode PRODUCTION = menu(_frame_.MAIN, 600, "PRODUCTION");

    /**/MenuNode makeOrderListAdmin = entry(_saleMenu.SALEMGMT, 20, "makeOrderListAdmin", __.join("order/list.jsf"));
    /**/MenuNode makeOrderAdmin = entry(_saleMenu.SALEMGMT, 21, "makeOrderAdmin", __.join("order/"));

    /**/MenuNode makeTaskListAdmin = entry(PRODUCTION, 10, "makeTaskListAdmin", __.join("task/list.jsf"));
    /**/MenuNode makeTaskAdmin = entry(PRODUCTION, 11, "makeTaskAdmin", __.join("task/"));

    /**/MenuNode materialPlanAdmin = entry(PURCHASE, 20, "materialPlanAdmin", __.join("plan/"));

    /**/MenuNode deliveryNoteListAdmin = entry(_saleMenu.SALEMGMT, 50, "deliveryNoteListAdmin", __.join("delivery/list.jsf"));
    /**/MenuNode deliveryNoteAdmin = entry(_saleMenu.SALEMGMT, 51, "deliveryNoteAdmin", __.join("delivery/"));

    /**/MenuNode makeProcessListAdmin = entry(PRODUCTION, 60, "makeProcessListAdmin", __.join("process/list.jsf"));
    /**/MenuNode makeProcessAdmin = entry(PRODUCTION, 61, "makeProcessAdmin", __.join("process/"));

    /**/MenuNode makeStepItemListAdmin = entry(PRODUCTION, 70, "makeStepItemListAdmin", __.join("stepItem/list.jsf"));

    /**/MenuNode batchImport = entry(PRODUCTION, 80, "batchImport", __.join("import/"));
    /**/MenuNode productionSchedule = entry(PRODUCTION, 90, "productionSchedule", __.join("schedule/"));

    static Location __Inv = WEB_APP.join(SEMInventoryModule.PREFIX_);

    /**/MenuNode factoryTakeIn = entry(_invMenu.INVENTORY, 90, "factoryTakeIn", __Inv.join("stock/FACTORY_IN/"));
    /**/MenuNode factoryTakeOut = entry(_invMenu.INVENTORY, 91, "factoryTakeOut", __Inv.join("stock/FACTORY_OUT/"));
}
