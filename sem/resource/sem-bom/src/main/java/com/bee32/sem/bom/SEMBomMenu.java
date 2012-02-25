package com.bee32.sem.bom;

import com.bee32.plover.ox1.dict.CommonDictController;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.SEMInventoryMenu;

public class SEMBomMenu
        extends MenuContribution {

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX_);

    static Location _(String path) {
        return WEB_APP.join(SEMBomModule.PREFIX_).join(path);
    }

    static MenuNode bomadmin = entry(SEMInventoryMenu.MATERIAL, 30, "bomadmin", _("part/"));

}
