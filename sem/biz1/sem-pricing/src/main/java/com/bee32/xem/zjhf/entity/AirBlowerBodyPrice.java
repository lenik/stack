package com.bee32.xem.zjhf.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.world.monetary.MCValue;

/**
 * 风机体价格
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "zjhf_air_blower_body_price_seq", allocationSize = 1)
public class AirBlowerBodyPrice extends UIEntityAuto<Long> {
    private static final long serialVersionUID = 1L;

    public static final int NUMBER_LENGTH = 30;

    AirBlowerType type;
    String number;
    MCValue value = new MCValue();

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
    @Embedded
    @AttributeOverrides({
            // { price_cc, price }
            @AttributeOverride(name = "currency", column = @Column(name = "value_cc")), //
            @AttributeOverride(name = "value", column = @Column(name = "value")) })
    public MCValue getValue() {
        return value;
    }

    public void setValue(MCValue value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
    }
}
