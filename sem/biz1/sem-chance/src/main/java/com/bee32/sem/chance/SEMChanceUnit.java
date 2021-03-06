package com.bee32.sem.chance;

import com.bee32.icsf.principal.IcsfPrincipalUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.chance.entity.*;
import com.bee32.sem.event.SEMEventUnit;
import com.bee32.sem.material.SEMMaterialUnit;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.process.SEMProcessUnit;
import com.bee32.sem.world.SEMWorldUnit;

/**
 * SEM 销售机会数据单元
 *
 * <p lang="en">
 * SEM Sales Chance Unit
 */
@ImportUnit({ IcsfPrincipalUnit.class, SEMEventUnit.class, SEMPeopleUnit.class, SEMWorldUnit.class,
        SEMMaterialUnit.class, SEMProcessUnit.class })
public class SEMChanceUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(ChanceActionStyle.class);
        add(ChanceCategory.class);
        add(ChanceSourceType.class);
        add(ChancePriority.class);
        add(ChanceStage.class);
        add(PurchaseRegulation.class);
        add(ProcurementMethod.class);

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
