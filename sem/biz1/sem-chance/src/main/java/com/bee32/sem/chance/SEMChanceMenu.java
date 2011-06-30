package com.bee32.sem.chance;

import com.bee32.plover.orm.ext.dict.CommonDictController;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMChanceMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX_);
    static Location CHANCE_ = WEB_APP.join(SEMChanceModule.PREFIX_);

    public static MenuNode CHANCE = menu(SEMFrameMenu.BIZ1, "chance");
    public static MenuNode CHANCE_DICT = menu(CHANCE, "dict");

    static MenuNode category = entry(CHANCE_DICT, "category", getDictIndex(ChanceCategory.class));
    static MenuNode sourceType = entry(CHANCE_DICT, "sourceType", getDictIndex(ChanceSourceType.class));
    static MenuNode actionStyle = entry(CHANCE_DICT, "actionStyle", getDictIndex(ChanceActionStyle.class));
    static MenuNode stage = entry(CHANCE_DICT, "stage", getDictIndex(ChanceStage.class));

    static MenuNode chance = entry(CHANCE, "chance", CHANCE_.join("chance/index-rich.jsf"));
    static MenuNode action = entry(CHANCE, "action", CHANCE_.join("action/index-rich.jsf"));

// static MenuNode competitor = entry(CHANCE, "competitor", CHANCE_.join("competitor/index.jsf "));

    @Override
    protected void preamble() {
    }

}
