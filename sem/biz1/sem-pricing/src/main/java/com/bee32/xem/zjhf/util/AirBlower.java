package com.bee32.xem.zjhf.util;

import java.math.BigDecimal;

import com.bee32.sem.pricing.util.PricingObject;
import com.bee32.xem.zjhf.entity.AirBlowerBodyPrice;
import com.bee32.xem.zjhf.entity.Motor;

/**
 * 风机
 *
 */
public class AirBlower
        extends PricingObject {

    private static final long serialVersionUID = 1L;

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

}
