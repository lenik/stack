package com.bee32.sem.uber;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.account.SEMAccountUnit;
import com.bee32.sem.asset.SEMAssetUnit;
import com.bee32.sem.chance.SEMChanceUnit;
import com.bee32.sem.purchase.SEMPurchaseUnit;
import com.bee32.sem.salary.SEMSalaryUnit;
import com.bee32.sem.track.SEMTrackUnit;
import com.bee32.xem.zjhf.SEMZjhfUnit;

/**
 * Import units of biz1, biz2, etc..
 */
@ImportUnit({ SEMChanceUnit.class, SEMPurchaseUnit.class, SEMAssetUnit.class, SEMAccountUnit.class, SEMSalaryUnit.class, SEMZjhfUnit.class, SEMTrackUnit.class })
public class SEMUberUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
    }

}
