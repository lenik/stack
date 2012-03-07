package com.bee32.sem.inventory;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

public class SEMInventorySamples2
        extends NormalSamples {

    public final VerifyPolicyPref stockVerifyPref = new VerifyPolicyPref();

    SEMInventorySamples inventories = predefined(SEMInventorySamples.class);

    @Override
    protected void wireUp() {
        stockVerifyPref.setType(StockOrder.class);
        stockVerifyPref.setPreferredPolicy(inventories.stockPolicy);
        stockVerifyPref.setDescription(PREFIX + "用库存审核策略对库存对象审核。");
    }

}
