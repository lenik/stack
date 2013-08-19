package com.bee32.sem.material;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

/**
 * 物料管理
 *
 * <p lang="en">
 */
public class SEMMaterialMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    static Location __ = WEB_APP.join(SEMMaterialModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    /**
     * 物料分类管理
     *
     * <p lang="en">
     */
    /*    */MenuNode category = entry(_frame_.BIZ1, 100, "category", __.join("category/"));

    /**
     * 物料管理
     *
     * <p lang="en">
     */
    /*    */MenuNode material = entry(_frame_.BIZ1, 101, "material", __.join("material/"));

}
