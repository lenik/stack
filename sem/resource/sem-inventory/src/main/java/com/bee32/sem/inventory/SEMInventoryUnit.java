package com.bee32.sem.inventory;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.asset.SEMAssetUnit;
import com.bee32.sem.file.SEMFileUnit;
import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.entity.StockInventoryXP;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockPeriod;
import com.bee32.sem.inventory.entity.StocktakingOrder;
import com.bee32.sem.inventory.entity.StocktakingOrderItem;
import com.bee32.sem.inventory.process.StockOrderVerifyPolicy;
import com.bee32.sem.inventory.tx.entity.StockJob;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.material.SEMMaterialUnit;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.process.SEMProcessUnit;
import com.bee32.sem.world.SEMWorldUnit;

@ImportUnit({ SEMProcessUnit.class, SEMPeopleUnit.class, SEMWorldUnit.class, SEMFileUnit.class, SEMAssetUnit.class,
        SEMMaterialUnit.class })
public class SEMInventoryUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(StockOrderVerifyPolicy.class);

        add(StockInventory.class);
        add(StockInventoryXP.class);
        add(StockPeriod.class);

        add(StockOrder.class);
        add(StockOrderItem.class);
        add(StocktakingOrder.class);
        add(StocktakingOrderItem.class);

        add(StockJob.class);
        add(StockTransfer.class);
        add(StockOutsourcing.class);
    }

}
