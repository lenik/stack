package com.bee32.xem.zjhf.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.sem.pricing.entity.PricingFormula;

/**
 * 风机价格公式
 *
 */
@Entity
@DiscriminatorValue("AIR")
public class AirBlowerPricingFormula extends PricingFormula {
    private static final long serialVersionUID = 1L;

}
