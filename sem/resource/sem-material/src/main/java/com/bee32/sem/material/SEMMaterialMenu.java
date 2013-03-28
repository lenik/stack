package com.bee32.sem.material;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMMaterialMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    static Location __ = WEB_APP.join(SEMMaterialModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /*    */MenuNode category = entry(_frame_.BIZ1, 100, "category", __.join("category/"));
    /*    */MenuNode material = entry(_frame_.BIZ1, 101, "material", __.join("material/"));

}
