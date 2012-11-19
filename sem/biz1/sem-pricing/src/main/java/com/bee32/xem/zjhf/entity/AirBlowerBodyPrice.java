package com.bee32.xem.zjhf.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 风机体价格
 */
@Entity
@Table(name = "zjhf_air_blower_body_price")
@SequenceGenerator(name = "idgen", sequenceName = "zjhf_air_blower_body_price_seq", allocationSize = 1)
public class AirBlowerBodyPrice extends UIEntityAuto<Long> {
    private static final long serialVersionUID = 1L;

    public static final int NUMBER_LENGTH = 30;

    AirBlowerType type;
    String number;
    BigDecimal value;

    /**
     * 风机类型
     *
     */
    @ManyToOne
    public AirBlowerType getType() {
        return type;
    }

    public void setType(AirBlowerType type) {
        this.type = type;
    }

    /**
     * 机号
     *
     */
    @Column(length = NUMBER_LENGTH)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 风机体价格
     *
     */
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
