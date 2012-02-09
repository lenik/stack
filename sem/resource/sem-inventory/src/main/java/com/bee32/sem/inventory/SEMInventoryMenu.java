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

    public static MenuNode MATERIAL = menu(SEMFrameMenu.RESOURCES, 1, "material");
    /*    */static MenuNode materialCategory = entry(MATERIAL, 1, "materialCategory", _("category/"));
    /*    */static MenuNode material = entry(MATERIAL, 3, "material", _("material/"));

    public static MenuNode INVENTORY = menu(SEMFrameMenu.RESOURCES, 2, "inventory");

    public static MenuNode SETTINGS = menu(INVENTORY, 1, "settings");
    /*    */static MenuNode verifyPolicy = entry(SEMProcessMenu.VERIFY_POLICY, 100, "verifyPolicy", _("verify/"));
    /*    */static MenuNode stockWarehouse = entry(SETTINGS, 1, "stockWarehouse", _("warehouse/"));
    /*    */static MenuNode stockLocation = entry(SETTINGS, 10, "stockLocation", _("location/"));

    public static MenuNode BUSINESS = menu(INVENTORY, 2, "business");
    /*    */static MenuNode init = entry(BUSINESS, 1, "init", _("stock/INIT/"));
    /*    */static MenuNode takeIn = entry(BUSINESS, 2, "takeIn", _stock(StockOrderSubject.TAKE_IN));
    /*    */static MenuNode takeOut = entry(BUSINESS, 3, "takeOut", _stock(StockOrderSubject.TAKE_OUT));
    /*    */static MenuNode factoryTakeIn = entry(BUSINESS, 4, "factoryTakeIn", _stock(StockOrderSubject.FACTORY_IN));
    /*    */static MenuNode factoryTakeOut = entry(BUSINESS, 5, "factoryTakeOut", _stock(StockOrderSubject.FACTORY_OUT));
    /*    */static MenuNode stocktaking = entry(BUSINESS, 6, "stocktaking", _("stock/STKD/"));
    /*    */static MenuNode transferOut = entry(BUSINESS, 7, "transferOut", _("stock/XFER_OUT/"));
    /*    */static MenuNode transferIn = entry(BUSINESS, 8, "transferIn", _("stock/XFER_IN/"));
    /*    */static MenuNode outsourcingOut = entry(BUSINESS, 9, "outsourcingOut", _("stock/OSP_OUT/"));
    /*    */static MenuNode outsourcingIn = entry(BUSINESS, 10, "outsourcingIn", _("stock/OSP_IN/"));

    public static MenuNode QUERY = menu(INVENTORY, 3, "query");
    /*    */static MenuNode stockQuery = entry(QUERY, 1, "stockQuery", _("query/"));

    @Override
    protected void preamble() {
    }

}
