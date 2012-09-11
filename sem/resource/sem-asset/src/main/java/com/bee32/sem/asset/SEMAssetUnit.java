package com.bee32.sem.asset;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.asset.entity.AccountInit;
import com.bee32.sem.asset.entity.AccountInitItem;
import com.bee32.sem.asset.entity.AccountSnapshot;
import com.bee32.sem.asset.entity.AccountSnapshotItem;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.world.SEMWorldUnit;

@ImportUnit({ SEMInventoryUnit.class, SEMPeopleUnit.class, SEMWorldUnit.class })
public class SEMAssetUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AccountInit.class);
        add(AccountInitItem.class);
        add(AccountSubject.class);
        add(AccountTicket.class);
        add(AccountTicketItem.class);
        add(AccountSnapshot.class);
        add(AccountSnapshotItem.class);
        add(FundFlow.class);
    }
}
