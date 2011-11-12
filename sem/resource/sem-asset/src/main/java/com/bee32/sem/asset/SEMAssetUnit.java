package com.bee32.sem.asset;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.world.SEMWorldUnit;

@ImportUnit({ SEMPeopleUnit.class, SEMWorldUnit.class })
public class SEMAssetUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AccountTicketItem.class);
        add(AccountSubject.class);
    }

}
