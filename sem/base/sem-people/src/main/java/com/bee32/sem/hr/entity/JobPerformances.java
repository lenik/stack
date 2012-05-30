package com.bee32.sem.hr.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class JobPerformances extends StandardSamples {
    public final JobPerformance idle = new JobPerformance("idle", "闲散的");
    public final JobPerformance normal = new JobPerformance("normal", "普通");
    public final JobPerformance crazy = new JobPerformance("crazy", "疯狂的");
    public final JobPerformance serious = new JobPerformance("serious", "认真的");

}
