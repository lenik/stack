package com.bee32.sem.opportunity;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.opportunity.entity.Competitor;
import com.bee32.sem.opportunity.entity.Opportunity;
import com.bee32.sem.opportunity.entity.OpportunityDetail;
import com.bee32.sem.opportunity.entity.OpportunityHistory;
import com.bee32.sems.crm.customer.SEMCustomerUnit;
import com.bee32.sems.org.SEMOrgUnit;

@ImportUnit({SEMOrgUnit.class, SEMCustomerUnit.class})
public class SEMOpportunityUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(OpportunityHistory.class);
        add(OpportunityDetail.class);
        add(Competitor.class);
        add(Opportunity.class);
    }

}
