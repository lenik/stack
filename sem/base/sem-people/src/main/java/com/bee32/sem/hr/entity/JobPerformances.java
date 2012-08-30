package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import com.bee32.plover.orm.sample.NormalSamples;

public class JobPerformances
        extends NormalSamples {
    public final JobPerformance idle = new JobPerformance("闲散的", BigDecimal.ZERO);
    public final JobPerformance normal = new JobPerformance("普通", BigDecimal.ZERO);
    public final JobPerformance crazy = new JobPerformance("疯狂的", BigDecimal.ZERO);
    public final JobPerformance serious = new JobPerformance("认真的", BigDecimal.ZERO);

}
