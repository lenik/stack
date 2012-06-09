package com.bee32.sem.make;

import com.bee32.plover.ox1.dict.CommonDictController;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.inventory.SEMInventoryMenu;

public class SEMMakeMenu
        extends MenuComposite {

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX_);

    static Location _(String path) {
        return WEB_APP.join(SEMMakeModule.PREFIX_).join(path);
    }

    static MenuNode makeStepNameAdmin = entry(SEMInventoryMenu.MATERIAL, 30, "makeStepNameAdmin", _("makeStepName/"));
    static MenuNode bomAdmin = entry(SEMInventoryMenu.MATERIAL, 40, "bomAdmin", _("part/"));

}
