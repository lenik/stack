package com.bee32.xem.zjhf;

import com.bee32.icsf.IcsfAccessUnit;
import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.pricing.entity.PricingFormula;
import com.bee32.sem.world.SEMWorldUnit;
import com.bee32.xem.zjhf.entity.AirBlowerBodyPrice;
import com.bee32.xem.zjhf.entity.AirBlowerPricingFormula;
import com.bee32.xem.zjhf.entity.AirBlowerType;
import com.bee32.xem.zjhf.entity.Motor;
import com.bee32.xem.zjhf.entity.MotorType;
import com.bee32.xem.zjhf.entity.ValvePricingFormula;
import com.bee32.xem.zjhf.entity.ValveType;

/**
 * 风机报价
 *
 * <p lang="en">
 */
@ImportUnit({ IcsfAccessUnit.class, SEMWorldUnit.class })
public class SEMZjhfUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(PricingFormula.class);
        add(AirBlowerBodyPrice.class);
        add(AirBlowerPricingFormula.class);
        add(AirBlowerType.class);
        add(Motor.class);
        add(MotorType.class);
        add(ValvePricingFormula.class);
        add(ValveType.class);

    }
}
