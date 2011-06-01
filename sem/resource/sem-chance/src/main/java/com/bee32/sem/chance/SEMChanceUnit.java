package com.bee32.sem.chance;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.Competitor;
import com.bee32.sem.chance.entity.Opportunity;
import com.bee32.sem.chance.entity.OpportunityHistory;
import com.bee32.sems.crm.customer.SEMCustomerUnit;
import com.bee32.sems.org.SEMOrgUnit;

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
