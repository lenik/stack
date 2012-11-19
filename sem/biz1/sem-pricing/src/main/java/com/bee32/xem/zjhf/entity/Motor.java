package com.bee32.xem.zjhf.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 电机
 *
 */
@Entity
@Table(name = "zjhf_motor")
@SequenceGenerator(name = "idgen", sequenceName = "zjhf_motor_seq", allocationSize = 1)
public class Motor implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int MODEL_SPEC_LENGTH = 100;

    MotorType type;
    String modelSpec;
    BigDecimal value;

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
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }



}
