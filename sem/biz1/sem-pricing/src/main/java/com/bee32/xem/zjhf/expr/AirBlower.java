package com.bee32.xem.zjhf.expr;

import java.math.BigDecimal;
import java.util.Map;

import com.bee32.plover.util.VariableEntry;
import com.bee32.sem.pricing.expr.PricingObject;
import com.bee32.xem.zjhf.entity.AirBlowerBodyPrice;
import com.bee32.xem.zjhf.entity.Motor;

/**
 * 风机
 *
 */
public class AirBlower
        extends PricingObject {

    AirBlowerBodyPrice bodyPrice;

    Motor motor;
    BigDecimal motorCount;

    /**
     * 风机体价格
     *
     * @return
     */
    public AirBlowerBodyPrice getBodyPrice() {
        return bodyPrice;
    }

    public void setBodyPrice(AirBlowerBodyPrice bodyPrice) {
        this.bodyPrice = bodyPrice;
    }

    /**
     * 电机
     *
     * @return
     */
    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    /**
     * 电机数量
     *
     * @return
     */
    public BigDecimal getMotorCount() {
        return motorCount;
    }

    public void setMotorCount(BigDecimal motorCount) {
        this.motorCount = motorCount;
    }

    @Override
    protected void populateVariables(Map<String, VariableEntry> varMap) {
        super.populateVariables(varMap);
        varMap.put("直径", new VariableEntry(bodyPrice.getValue()));
        varMap.put("电机数量", new VariableEntry(motorCount));
        varMap.put("电机价格", new VariableEntry(motor.getValue()));
    }

}
