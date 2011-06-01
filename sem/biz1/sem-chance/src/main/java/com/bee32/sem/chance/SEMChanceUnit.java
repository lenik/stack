package com.bee32.sem.chance;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.Competitor;

@ImportUnit({SEMOrgUnit.class, SEMCustomerUnit.class})
public class SEMChanceUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(OpportunityHistory.class);
        add(ChanceParty.class);
        add(Competitor.class);
        add(Opportunity.class);
    }

}
