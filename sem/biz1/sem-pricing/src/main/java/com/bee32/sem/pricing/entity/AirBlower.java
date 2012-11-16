package com.bee32.sem.pricing.entity;

import java.math.BigDecimal;

/**
 * 风机
 *
 */
public class AirBlower extends PricingObject {

    private static final long serialVersionUID = 1L;

    BigDecimal motorAmount;

    AirBlowerBodyPrice bodyPrice;
    Motor motor;

    /**
     * 电机数量
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
     * @return
     */
    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }




}
