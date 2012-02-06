package com.bee32.sem.inventory;

import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

@ImportSamples(SEMInventorySamples.class)
public class SEMInventorySamples2
        extends SampleContribution {

    static VerifyPolicyPref stockVerifyPref = new VerifyPolicyPref();

    static {
        stockVerifyPref.setType(StockOrder.class);
        stockVerifyPref.setPreferredPolicy(SEMInventorySamples.stockPolicy);
        stockVerifyPref.setDescription("用库存审核策略对库存对象审核。");
    }

    @Override
    protected void preamble() {
        add(stockVerifyPref);
    }

}
