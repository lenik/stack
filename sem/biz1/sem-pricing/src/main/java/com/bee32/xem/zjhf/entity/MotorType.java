package com.bee32.xem.zjhf.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 电机类型
 *
 * 风机所使用的电机类型
 *
 * 使用父类中的label 和 description
 *
 */
@Entity
@Table(name = "zjhf_motor_type")
@SequenceGenerator(name = "idgen", sequenceName = "motor_type_seq", allocationSize = 1)
public class MotorType
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public MotorType() {
        super();
    }

    public MotorType(String name) {
        super(name);
    }

    public MotorType(String label, String desc) {
        super();
        this.label = label;
        this.description = desc;
    }
}
