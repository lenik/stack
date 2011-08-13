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

    static Location INVENTORY_ = WEB_APP.join(SEMInventoryModule.PREFIX_);

    public static MenuNode INVENTORY = menu(SEMFrameMenu.RESOURCE, "inventory");


        public static MenuNode SETTINGS = menu(INVENTORY, 1, "settings");
            static MenuNode materialCategory = entry(SETTINGS, 1, "materialCategory", INVENTORY_.join("category/"));
            static MenuNode stockWarehouse = entry(SETTINGS, 1, "stockWarehouse", INVENTORY_.join("warehouse/"));
            static MenuNode stockLocation = entry(SETTINGS, 10, "stockLocation", INVENTORY_.join("location/"));


        public static MenuNode MATERIAL = menu(INVENTORY, 10, "material");
            static MenuNode materialAdmin = entry(MATERIAL, 1, "materialAdmin", INVENTORY_.join("material/"));



        public static MenuNode BUSINESS = menu(INVENTORY, 20, "business");
            static MenuNode init = entry(BUSINESS, 1, "init", INVENTORY_.join("init/"));
            static MenuNode takeIn = entry(BUSINESS, 2, "takeIn", INVENTORY_.join("take/?subject=" + StockOrderSubject.TAKE_IN.getValue()));
            static MenuNode takeOut = entry(BUSINESS, 3, "takeOut", INVENTORY_.join("take/?subject=" + StockOrderSubject.TAKE_OUT.getValue()));
            static MenuNode stocktaking = entry(BUSINESS, 4, "stocktaking", INVENTORY_.join("stocktaking/"));
            static MenuNode transferOut = entry(BUSINESS, 5, "transferOut", INVENTORY_.join("transferOut/"));
            static MenuNode transferIn = entry(BUSINESS, 6, "transferIn", INVENTORY_.join("transferIn/"));


    @Override
    protected void preamble() {
    }

}
