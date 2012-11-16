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

    BigDecimal motorAmount;

    AirBlowerBodyPrice bodyPrice;
    Motor motor;

    /**
     * 电机数量
     *
     * @return
     */
    public BigDecimal getMotorAmount() {
        return motorAmount;
    }

    public void setMotorAmount(BigDecimal motorAmount) {
        this.motorAmount = motorAmount;
    }

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

}
