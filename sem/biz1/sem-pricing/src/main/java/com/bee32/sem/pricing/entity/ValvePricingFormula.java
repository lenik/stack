package com.bee32.sem.pricing.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 阀价格公式
 */
@Entity
@DiscriminatorValue("VLV")
public class ValvePricingFormula extends PricingFormula {
    private static final long serialVersionUID = 1L;

    ValveType type;

    /**
     * 阀类型
     */
    @ManyToOne
    public ValveType getType() {
        return type;
    }

    public void setType(ValveType type) {
        this.type = type;
    }


}
