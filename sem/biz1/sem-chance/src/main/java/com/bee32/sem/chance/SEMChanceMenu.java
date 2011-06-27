package com.bee32.sem.chance;

import com.bee32.plover.orm.ext.dict.CommonDictController;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.frame.menu.SEMMainMenu;

public class SEMChanceMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX_);
    static Location CHANCE = WEB_APP.join(SEMChanceModule.PREFIX_);

    public static MenuNode cha = menu(SEMMainMenu.MAIN, "chance");
    public static MenuNode chaDict = menu(SEMMainMenu.ADMIN, "chance");

    static MenuNode category = entry(chaDict, "category", getDictIndex(ChanceCategory.class));
    static MenuNode sourceType = entry(chaDict, "sourceType", getDictIndex(ChanceSourceType.class));
    static MenuNode actionStyle = entry(chaDict, "actionStyle", getDictIndex(ChanceActionStyle.class));
    static MenuNode stage = entry(chaDict, "stage", getDictIndex(ChanceStage.class));

    static MenuNode chance = entry(cha, "chance", CHANCE.join("chance/index-rich.do"));
    static MenuNode action = entry(cha, "action", CHANCE.join("action/index-rich.do"));
    static MenuNode competitor = entry(cha, "competitor", CHANCE.join("competitor/index.do"));

    @Override
    protected void preamble() {
    }

}
