package com.bee32.sem.chance;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.ChanceQuotation;
import com.bee32.sem.chance.entity.ChanceQutationItem;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.entity.Competitor;
import com.bee32.sem.inventory.SEMInventoryUnit;

@ImportUnit({ SEMInventoryUnit.class })
public class SEMChanceUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(ChanceActionStyle.class);
        add(ChanceCategory.class);
        add(ChanceSourceType.class);
        add(ChanceStage.class);

        add(Chance.class);
        add(ChanceParty.class);
        add(ChanceAction.class);
        add(Competitor.class);

        add(ChanceQuotation.class);
        add(ChanceQutationItem.class);
    }

}
