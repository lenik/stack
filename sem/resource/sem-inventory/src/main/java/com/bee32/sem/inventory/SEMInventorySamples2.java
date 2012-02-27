package com.bee32.sem.inventory;

import javax.inject.Inject;

import com.bee32.plover.orm.util.NormalSamples;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

public class SEMInventorySamples2
        extends NormalSamples {

    public final VerifyPolicyPref stockVerifyPref = new VerifyPolicyPref();

    @Inject
    SEMInventorySamples inventories;

    @Override
    protected void wireUp() {
        stockVerifyPref.setType(StockOrder.class);
        stockVerifyPref.setPreferredPolicy(inventories.stockPolicy);
        stockVerifyPref.setDescription("用库存审核策略对库存对象审核。");
    }

}
