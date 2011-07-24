package com.bee32.sem.inventory;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMInventoryMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location INVENTORY_ = WEB_APP.join(SEMInventoryModule.PREFIX_);

    public static MenuNode INVENTORY = menu(SEMFrameMenu.MAIN, "inventory");
    public static MenuNode MATERIAL = menu(INVENTORY, "material");
    public static MenuNode SETTINGS = menu(INVENTORY, "settings");

    static MenuNode materialAdmin = entry(MATERIAL, 1, "materialAdmin", INVENTORY_.join("material/"));

    static MenuNode materialCategory = entry(SETTINGS, 1, "materialCategory", INVENTORY_.join("category/"));
    static MenuNode stockWarehouse = entry(SETTINGS, 1, "stockWarehouse", INVENTORY_.join("warehouse/"));
    static MenuNode stockLocation = entry(SETTINGS, 10, "stockLocation", INVENTORY_.join("location/"));

    @Override
    protected void preamble() {
    }

}
