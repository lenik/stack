package com.bee32.sem.chance;

import com.bee32.plover.orm.ext.dict.CommonDictController;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMChanceMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location DICT = WEB_APP.join(CommonDictController.PREFIX_);
    static Location CHANCE = WEB_APP.join(SEMChanceModule.PREFIX_);

    @Contribution("biz1/chance")
    MenuEntry chance = new MenuEntry("chance", CHANCE.join("chance/index-rich.do"));

    @Contribution("biz1/chance")
    MenuEntry action = new MenuEntry("action", CHANCE.join("action/index-rich.do"));

    @Contribution("biz1/chance")
    MenuEntry competitor = new MenuEntry("competitor", CHANCE.join("competitor/index.do"));

    // Dicts...

    @Contribution("biz1/chance")
    MenuEntry category = new MenuEntry("category", getDictIndex(ChanceCategory.class));

    @Contribution("biz1/chance")
    MenuEntry sourceType = new MenuEntry("sourceType", getDictIndex(ChanceSourceType.class));

    @Contribution("biz1/chance")
    MenuEntry actionStyle = new MenuEntry("actionStyle", getDictIndex(ChanceActionStyle.class));

    @Contribution("biz1/chance")
    MenuEntry stage = new MenuEntry("stage", getDictIndex(ChanceStage.class));

    @Override
    protected void preamble() {
    }

}
