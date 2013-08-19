package com.bee32.xem.zjhf.entity;

import com.bee32.plover.orm.sample.StandardSamples;

/**
 * 电机类型样本
 *
 * <p lang="en">
 */
public class MotorTypes
        extends StandardSamples {

    public final MotorType Y_B3_B5 = new MotorType("Y-B3/B5", "");
    public final MotorType Y_B35 = new MotorType("Y-B35", "");
    public final MotorType Y_B3_B5_ONE = new MotorType("Y-B3/B5单相", "");
    public final MotorType Y_B3_B5_NOEXP = new MotorType("Y-B3/B5防爆", "");
    public final MotorType OUTER_ROTOR_ONE = new MotorType("外转子单相", "");
    public final MotorType OUTER_ROTOR_THREE = new MotorType("外转子三相", "");
    public final MotorType OUTER_ROTOR_NOEXP = new MotorType("外转子防爆", "");
}
