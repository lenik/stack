package com.bee32.sem.chance;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMChanceMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(SEMChanceModule.PREFIX_);

    public transient MenuNode CHANCE = _frame_.BIZ1;

    MenuNode chance = entry(CHANCE, 30, "chance", prefix.join("chance/"));
    MenuNode action = entry(CHANCE, 31, "action", prefix.join("action/"));

    public MenuNode CHANCE_DICT = menu(CHANCE, 32, "chanceDicts");
    /*    */MenuNode category = entry(CHANCE_DICT, 1, "category", getDictIndex(ChanceCategory.class));
    /*    */MenuNode sourceType = entry(CHANCE_DICT, 2, "sourceType", getDictIndex(ChanceSourceType.class));
    /*    */MenuNode actionStyle = entry(CHANCE_DICT, 3, "actionStyle", getDictIndex(ChanceActionStyle.class));
    /*    */MenuNode stage = entry(CHANCE_DICT, 4, "stage", getDictIndex(ChanceStage.class));

    public MenuNode PRICE = menu(CHANCE, 32, "price");

    // MenuNode competitor = entry(CHANCE, "competitor", CHANCE_.join("competitor/ "));

}
