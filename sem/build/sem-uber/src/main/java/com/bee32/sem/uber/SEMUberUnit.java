package com.bee32.sem.uber;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.account.SEMAccountUnit;
import com.bee32.sem.asset.SEMAssetUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.purchase.SEMPurchaseUnit;
import com.bee32.sem.wage.SEMWageUnit;

/**
 * Import units of biz1, biz2, etc..
 */
@ImportUnit({ SEMChanceUnit.class, SEMPurchaseUnit.class, SEMAssetUnit.class, SEMAccountUnit.class, SEMWageUnit.class })
public class SEMUberUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
    }

}
