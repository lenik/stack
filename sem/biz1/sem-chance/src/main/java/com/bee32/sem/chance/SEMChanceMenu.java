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

    @Contribution("crmcustomer")
    MenuEntry chanceAdmin = new MenuEntry("chanceAdmin");

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry chanceAction = new MenuEntry("chanceAction", CHANCE.join("action/main.do"));

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry dictionary = new MenuEntry("dictionary");

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry chance = new MenuEntry("chance", CHANCE.join("chance/main.do"));

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry competitor = new MenuEntry("competitor", CHANCE.join("competitor/index.do"));

    @Contribution("crmcustomer/chanceAdmin/dictionary")
    MenuEntry categories = new MenuEntry("categories", getDictIndex(ChanceCategory.class));

    @Contribution("crmcustomer/chanceAdmin/dictionary")
    MenuEntry sourceTypes = new MenuEntry("sourceTypes", getDictIndex(ChanceSourceType.class));

    @Contribution("crmcustomer/chanceAdmin/dictionary")
    MenuEntry actionStyles = new MenuEntry("actionStyles", getDictIndex(ChanceActionStyle.class));

    @Contribution("crmcustomer/chanceAdmin/dictionary")
    MenuEntry stages = new MenuEntry("stages", getDictIndex(ChanceStage.class));

    @Override
    protected void preamble() {
    }

}
