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

    static Location CHANCE = WEB_APP.join("customer/chance/");
    static Location CHANCEACTION = WEB_APP.join("customer/chanceAction/");
    static Location COMPETITOR = WEB_APP.join("customer/competitor/");
    static Location DICT = WEB_APP.join(CommonDictController.PREFIX);

    @Contribution("crmcustomer")
    MenuEntry chanceAdmin = new MenuEntry("chanceAdmin");

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry chanceAction = new MenuEntry("chanceAction", CHANCEACTION.join("chanceActionAdminjsf.do"));

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry chance = new MenuEntry("chance", CHANCE.join("index.do"));

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry competitor = new MenuEntry("competitor", COMPETITOR.join("index.do"));

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry categories = new MenuEntry("categories", DICT.join(ABBR.abbr(ChanceCategory.class) + "index.do"));

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry sourceTypes = new MenuEntry("sourceTypes", DICT.join(ABBR.abbr(ChanceSourceType.class) + "index.do"));

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry actionStyles = new MenuEntry("actionStyles", DICT.join(ABBR.abbr(ChanceActionStyle.class) + "index.do"));

    @Contribution("crmcustomer/chanceAdmin")
    MenuEntry stages = new MenuEntry("stages", DICT.join(ABBR.abbr(ChanceStage.class) + "index.do"));

    @Override
    protected void preamble() {
    }

}
