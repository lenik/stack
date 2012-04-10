package com.bee32.sem.inventory;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.process.SEMProcessMenu;

public class SEMInventoryMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location _(String path) {
        return WEB_APP.join(SEMInventoryModule.PREFIX_).join(path);
    }

    static Location _stock(StockOrderSubject subject) {
        return _("stock/?subject=" + subject.getValue());
    }

    public static MenuNode MATERIAL = menu(SEMFrameMenu.MAIN, 430, "material");
    /*    */static MenuNode materialCategory = entry(MATERIAL, 1, "materialCategory", _("category/"));
    /*    */static MenuNode material = entry(MATERIAL, 3, "material", _("material/"));

    public static MenuNode INVENTORY = menu(SEMFrameMenu.MAIN, 440, "inventory");
    /*    */static MenuNode init = entry(INVENTORY, 10, "init", _("stock/INIT/"));
    /*    */static MenuNode takeIn = entry(INVENTORY, 20, "takeIn", _stock(StockOrderSubject.TAKE_IN));
    /*    */static MenuNode takeOut = entry(INVENTORY, 21, "takeOut", _stock(StockOrderSubject.TAKE_OUT));
    /*    */static MenuNode factoryTakeIn = entry(INVENTORY, 40, "factoryTakeIn", _stock(StockOrderSubject.FACTORY_IN));
    /*    */static MenuNode factoryTakeOut = entry(INVENTORY, 41, "factoryTakeOut", _stock(StockOrderSubject.FACTORY_OUT));
    /*    */static MenuNode planOut = entry(INVENTORY, 42, "planOut", _stock(StockOrderSubject.PLAN_OUT));
    /*    */static MenuNode stocktaking = entry(INVENTORY, 60, "stocktaking", _("stock/STKD/"));
    /*    */static MenuNode transferOut = entry(INVENTORY, 70, "transferOut", _("stock/XFER_OUT/"));
    /*    */static MenuNode transferIn = entry(INVENTORY, 71, "transferIn", _("stock/XFER_IN/"));
    /*    */static MenuNode outsourcingOut = entry(INVENTORY, 80, "outsourcingOut", _("stock/OSP_OUT/"));
    /*    */static MenuNode outsourcingIn = entry(INVENTORY, 81, "outsourcingIn", _("stock/OSP_IN/"));
    /*    */static MenuNode __1 = _separator_(INVENTORY, 100);
    /*    */static MenuNode stockQuery = entry(INVENTORY, 110, "stockQuery", _("query/"));
    /*    */static MenuNode unqualifiedQuery = entry(INVENTORY, 120, "unqualifiedQuery", _("unqualifiedQuery/"));
    /*    */static MenuNode __2 = _separator_(INVENTORY, 999);

    public static MenuNode SETTINGS = menu(INVENTORY, 1000, "settings");
    /*    */static MenuNode stockWarehouse = entry(SETTINGS, 1, "stockWarehouse", _("warehouse/"));
    /*    */static MenuNode stockLocation = entry(SETTINGS, 10, "stockLocation", _("location/"));

    /*    */static MenuNode verifyPolicy = entry(SEMProcessMenu.VERIFY_POLICY, 100, "verifyPolicy", _("verify/"));

}
