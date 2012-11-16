package com.bee32.xem.zjhf.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.sem.world.monetary.MCValue;

/**
 * 电机
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "motor_seq", allocationSize = 1)
public class Motor implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int MODEL_SPEC_LENGTH = 100;

    MotorType type;
    String modelSpec;
    MCValue value;

    /**
     * 电机类型
     */
    @ManyToOne
    public MotorType getType() {
        return type;
    }

    public void setType(MotorType type) {
        this.type = type;
    }

    /**
     * 规格型号
     */
    @Column(length = MODEL_SPEC_LENGTH)
    public String getModelSpec() {
        return modelSpec;
    }

    public void setModelSpec(String modelSpec) {
        this.modelSpec = modelSpec;
    }

    /**
     * 电机价格
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
