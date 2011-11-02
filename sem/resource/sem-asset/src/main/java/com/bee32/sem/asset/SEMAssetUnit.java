package com.bee32.sem.asset;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.asset.entity.AssetRecord;
import com.bee32.sem.asset.entity.AssetSubject;
import com.bee32.sem.asset.entity.BankAccount;
import com.bee32.sem.asset.entity.PayMethod;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.world.SEMWorldUnit;

@ImportUnit({ SEMPeopleUnit.class, SEMWorldUnit.class })
public class SEMAssetUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AssetRecord.class);
        add(AssetSubject.class);
        add(BankAccount.class);
        add(PayMethod.class);
    }

}
