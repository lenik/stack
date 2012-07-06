package com.bee32.sem.chance.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class PurchaseRegulations
        extends StandardSamples {

    public final PurchaseRegulation OTHER = new PurchaseRegulation("Other", "其他");
    public final PurchaseRegulation BRAND = new PurchaseRegulation("Brnad", "品牌优先");
    public final PurchaseRegulation PRICE = new PurchaseRegulation("Price", "价格优先");
    public final PurchaseRegulation EFFECTIVE = new PurchaseRegulation("Effective", "性价比优先");
}
