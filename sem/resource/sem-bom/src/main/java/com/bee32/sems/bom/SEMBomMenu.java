package com.bee32.sems.bom;

import com.bee32.plover.orm.ext.dict.CommonDictController;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMBomMenu
        extends MenuContribution {

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX_);
    static Location BOM_ = WEB_APP.join(SEMBomModule.PREFIX_);

    public static MenuNode BOM = menu(SEMFrameMenu.RESOURCE, "bom");

    static MenuNode bomadmin = entry(BOM, "bomadmin", WEB_APP.join("bom/bomAdminJsf.htm"));

    @Override
    protected void preamble() {
    }

}
