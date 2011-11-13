package com.bee32.sem.asset;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.AccountTicketItem;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.asset.entity.StockPurchase;
import com.bee32.sem.asset.entity.StockSale;
import com.bee32.sem.asset.entity.StockTrade;
import com.bee32.sem.asset.entity.StockTradeItem;
import com.bee32.sem.inventory.SEMInventoryUnit;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.world.SEMWorldUnit;

@ImportUnit({ SEMInventoryUnit.class, SEMPeopleUnit.class, SEMWorldUnit.class })
public class SEMAssetUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AccountSubject.class);
        add(AccountTicket.class);
        add(AccountTicketItem.class);
        add(BudgetRequest.class);
        add(StockPurchase.class);
        add(StockSale.class);
        add(StockTrade.class);
        add(StockTradeItem.class);
    }

}
