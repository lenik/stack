package com.bee32.sem.inventory;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.entity.StockOrderSubject;

public class SEMInventoryMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location _(String path) {
        return WEB_APP.join(SEMInventoryModule.PREFIX_).join(path);
    }

    static Location _take(StockOrderSubject subject) {
        return _("take/?subject=" + subject.getValue());
    }

    public static MenuNode MATERIAL = menu(SEMFrameMenu.RESOURCES, 1, "material");
    /*    */static MenuNode materialCategory = entry(MATERIAL, 1, "materialCategory", _("category/"));
    /*    */static MenuNode material = entry(MATERIAL, 3, "material", _("material/"));

    public static MenuNode INVENTORY = menu(SEMFrameMenu.RESOURCES, 2, "inventory");

    public static MenuNode SETTINGS = menu(INVENTORY, 1, "settings");
    /*    */static MenuNode verifyPolicy = entry(SETTINGS, 1, "verifyPolicy", _("verifyPolicy/"));
    /*    */static MenuNode stockWarehouse = entry(SETTINGS, 1, "stockWarehouse", _("warehouse/"));
    /*    */static MenuNode stockLocation = entry(SETTINGS, 10, "stockLocation", _("location/"));

    public static MenuNode BUSINESS = menu(INVENTORY, 2, "business");
    /*    */static MenuNode init = entry(BUSINESS, 1, "init", _("init/"));
    /*    */static MenuNode takeIn = entry(BUSINESS, 2, "takeIn", _take(StockOrderSubject.TAKE_IN));
    /*    */static MenuNode takeOut = entry(BUSINESS, 3, "takeOut", _take(StockOrderSubject.TAKE_OUT));
    /*    */static MenuNode factoryTakeIn = entry(BUSINESS, 4, "factoryTakeIn", _take(StockOrderSubject.FACTORY_IN));
    /*    */static MenuNode factoryTakeOut = entry(BUSINESS, 5, "factoryTakeOut", _take(StockOrderSubject.FACTORY_OUT));
    /*    */static MenuNode stocktaking = entry(BUSINESS, 6, "stocktaking", _("stocktaking/"));
    /*    */static MenuNode transferOut = entry(BUSINESS, 7, "transferOut", _("transferOut/"));
    /*    */static MenuNode transferIn = entry(BUSINESS, 8, "transferIn", _("transferIn/"));
    /*    */static MenuNode outsourcingOut = entry(BUSINESS, 9, "outsourcingOut", _("outsourcingOut/"));
    /*    */static MenuNode outsourcingIn = entry(BUSINESS, 10, "outsourcingIn", _("outsourcingIn/"));

    public static MenuNode QUERY = menu(INVENTORY, 3, "query");
    /*    */static MenuNode stockQuery = entry(QUERY, 1, "stockQuery", _("stockQuery/"));

    @Override
    protected void preamble() {
    }

}
