package com.bee32.sems.bom;

import com.bee32.plover.orm.ext.dict.CommonDictController;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.SEMInventoryMenu;

public class SEMBomMenu
        extends MenuContribution {

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX_);
    static Location BOM_ = WEB_APP.join(SEMBomModule.PREFIX_);

    static MenuNode bomadmin = entry(SEMInventoryMenu.MATERIAL, "bomadmin", BOM_.join("component/"));

    @Override
    protected void preamble() {
    }

}
