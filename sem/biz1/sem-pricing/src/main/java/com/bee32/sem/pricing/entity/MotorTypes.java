package com.bee32.sem.pricing.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class MotorTypes
        extends StandardSamples {

    public final MotorType Y_B3_B5 = new MotorType(10, "Y-B3/B5", "Y-B3/B5");
    public final MotorType Y_B35 = new MotorType(20, "Y-B35", "Y-B35");
    public final MotorType Y_B3_B5_ONE = new MotorType(30, "Y-B3/B5-ONE", "Y-B3/B5单相");
    public final MotorType Y_B3_B5_NOEXP = new MotorType(40, "Y-B3/B5-NOEXP", "Y-B3/B5防爆");
    public final MotorType OUTER_ROTOR_ONE = new MotorType(50, "OUTER_ROTOR_ONE", "外转子单相");
    public final MotorType OUTER_ROTOR_THREE = new MotorType(60, "OUTER_ROTOR_THREE", "外转子三相");
    public final MotorType OUTER_ROTOR_NOEXP = new MotorType(70, "OUTER_ROTOR_NOEXP", "外转子防爆");
}
