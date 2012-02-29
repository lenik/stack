package com.bee32.sem.chance;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceCompetitor;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.entity.WantedProduct;
import com.bee32.sem.chance.entity.WantedProductAttribute;
import com.bee32.sem.chance.entity.WantedProductQuotation;
import com.bee32.sem.chance.entity.WantedProductXP;
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
        add(ChanceCompetitor.class);

        add(WantedProduct.class);
        add(WantedProductXP.class);
        add(WantedProductAttribute.class);
        add(WantedProductQuotation.class);
    }

}
