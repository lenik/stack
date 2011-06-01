package com.bee32.sem.chance;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.entity.Competitor;
import com.bee32.sem.people.SEMPeopleUnit;

@ImportUnit({ SEMPeopleUnit.class })
public class SEMChanceUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(ChanceAction.class);
        add(ChanceParty.class);
        add(Competitor.class);
        add(Chance.class);
    }

}
