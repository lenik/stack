package com.bee32.xem.zjhf;


import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.pricing.entity.PricingFormula;
import com.bee32.xem.zjhf.entity.AirBlowerBodyPrice;
import com.bee32.xem.zjhf.entity.AirBlowerPricingFormula;
import com.bee32.xem.zjhf.entity.AirBlowerType;
import com.bee32.xem.zjhf.entity.Motor;
import com.bee32.xem.zjhf.entity.MotorType;
import com.bee32.xem.zjhf.entity.ValvePricingFormula;
import com.bee32.xem.zjhf.entity.ValveType;

@Oid({ 3, 15, SEMOids.Resource, SEMOids.resource.Asset })
public class SEMZjhfModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/6/5";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(PricingFormula.class, "pricingFormula");
        declareEntityPages(AirBlowerBodyPrice.class, "airBlowerBodyPrice");
        declareEntityPages(AirBlowerPricingFormula.class, "airBlowerPricingFormula");
        declareEntityPages(AirBlowerType.class, "airBlowerType");
        declareEntityPages(Motor.class, "motor");
        declareEntityPages(MotorType.class, "motorType");
        declareEntityPages(ValvePricingFormula.class, "valvePricingFormula");
        declareEntityPages(ValveType.class, "valveType");

    }

}
