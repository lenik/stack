package com.bee32.sem.asset;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.asset.entity.AccountInit;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.asset.entity.StockPurchase;
import com.bee32.sem.asset.entity.StockSale;
import com.bee32.sem.asset.entity.StockTrade;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Resource, SEMOids.resource.Asset })
public class SEMAssetModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/3/3";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(AccountInit.class, "init");
        declareEntityPages(AccountTicket.class, "ticket");
        declareEntityPages(BudgetRequest.class, "request");
        declareEntityPages(StockTrade.class, "stock-trade");
        declareEntityPages(StockSale.class, "stock-sale");
        declareEntityPages(StockPurchase.class, "stock-purchase");
    }

}
