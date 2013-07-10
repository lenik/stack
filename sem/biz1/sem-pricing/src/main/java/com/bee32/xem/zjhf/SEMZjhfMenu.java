package com.bee32.xem.zjhf;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMZjhfMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMZjhfModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode PRICING = menu(_frame_.MAIN, 610, "PRICING");

    MenuNode airBlowerType = entry(PRICING, 1, "airBlowerType", __.join("airBlowerType/"));
    MenuNode motorType = entry(PRICING, 1, "motorType", __.join("motorType/"));
    MenuNode valveType = entry(PRICING, 1, "valveType", __.join("valveType/"));

    MenuNode quoteImport = entry(PRICING, 1, "quoteImport", __.join("quoteImport/"));

}
