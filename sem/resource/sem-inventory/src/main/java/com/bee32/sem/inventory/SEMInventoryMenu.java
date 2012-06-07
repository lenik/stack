package com.bee32.sem.inventory;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.process.SEMProcessMenu;

public class SEMInventoryMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    static Location __ = WEB_APP.join(SEMInventoryModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    SEMProcessMenu _process_ = require(SEMProcessMenu.class);

    static Location _stock(StockOrderSubject subject) {
        return __.join("stock/?subject=" + subject.getValue());
    }

    public MenuNode MATERIAL = menu(_frame_.MAIN, 430, "MATERIAL");
    /*    */MenuNode category = entry(MATERIAL, 1, "category", __.join("category/"));
    /*    */MenuNode material = entry(MATERIAL, 3, "material", __.join("material/"));

    public MenuNode INVENTORY = menu(_frame_.MAIN, 440, "INVENTORY");
    /*    */MenuNode init = entry(INVENTORY, 10, "init", __.join("stock/INIT/"));
    /*    */MenuNode takeIn = entry(INVENTORY, 20, "takeIn", _stock(StockOrderSubject.TAKE_IN));
    /*    */MenuNode takeOut = entry(INVENTORY, 21, "takeOut", _stock(StockOrderSubject.TAKE_OUT));
    /*    */MenuNode factoryTakeIn = entry(INVENTORY, 40, "factoryTakeIn", _stock(StockOrderSubject.FACTORY_IN));
    /*    */MenuNode factoryTakeOut = entry(INVENTORY, 41, "factoryTakeOut", _stock(StockOrderSubject.FACTORY_OUT));
    /*    */MenuNode planOut = entry(INVENTORY, 42, "planOut", _stock(StockOrderSubject.PLAN_OUT));
    /*    */MenuNode stocktaking = entry(INVENTORY, 60, "stocktaking", __.join("stock/STKD/"));
    /*    */MenuNode transferOut = entry(INVENTORY, 70, "transferOut", __.join("stock/XFER_OUT/"));
    /*    */MenuNode transferIn = entry(INVENTORY, 71, "transferIn", __.join("stock/XFER_IN/"));
    /*    */MenuNode outsourcingOut = entry(INVENTORY, 80, "outsourcingOut", __.join("stock/OSP_OUT/"));
    /*    */MenuNode outsourcingIn = entry(INVENTORY, 81, "outsourcingIn", __.join("stock/OSP_IN/"));
    /*    */MenuNode __1 = _separator_(INVENTORY, 100);
    /*    */MenuNode stockQuery = entry(INVENTORY, 110, "stockQuery", __.join("query/"));
    /*    */MenuNode unqualifiedQuery = entry(INVENTORY, 120, "unqualifiedQuery", __.join("unqualifiedQuery/"));
    /*    */MenuNode __2 = _separator_(INVENTORY, 999);

    public MenuNode SETTINGS = menu(INVENTORY, 1000, "SETTINGS");
    /*    */MenuNode stockWarehouse = entry(SETTINGS, 1, "stockWarehouse", __.join("warehouse/"));
    /*    */MenuNode stockLocation = entry(SETTINGS, 10, "stockLocation", __.join("location/"));

    /*    */MenuNode verifyPolicy = entry(_process_.VERIFY_POLICY, 100, "verifyPolicy", __.join("verify/"));

}
