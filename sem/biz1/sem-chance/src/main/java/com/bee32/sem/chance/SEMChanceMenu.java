package com.bee32.sem.chance;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.dict.CommonDictController;
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

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX_);
    static Location CHANCE_ = WEB_APP.join(SEMChanceModule.PREFIX_);
// static Location PRICE_ = WEB_APP.join(SEMChanceModule.PREFIX_);

    public static transient MenuNode CHANCE = SEMFrameMenu.BIZ1;

    static MenuNode chance = entry(CHANCE, 30, "chance", CHANCE_.join("chance/"));
    static MenuNode action = entry(CHANCE, 31, "action", CHANCE_.join("action/"));

    public static MenuNode CHANCE_DICT = menu(CHANCE, 32, "chanceDicts");
    /*    */static MenuNode category = entry(CHANCE_DICT, 1, "category", getDictIndex(ChanceCategory.class));
    /*    */static MenuNode sourceType = entry(CHANCE_DICT, 2, "sourceType", getDictIndex(ChanceSourceType.class));
    /*    */static MenuNode actionStyle = entry(CHANCE_DICT, 3, "actionStyle", getDictIndex(ChanceActionStyle.class));
    /*    */static MenuNode stage = entry(CHANCE_DICT, 4, "stage", getDictIndex(ChanceStage.class));

    public static MenuNode PRICE = menu(CHANCE, 32, "price");

    // static MenuNode competitor = entry(CHANCE, "competitor", CHANCE_.join("competitor/ "));

}
