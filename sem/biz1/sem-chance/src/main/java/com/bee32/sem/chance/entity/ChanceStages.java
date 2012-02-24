package com.bee32.sem.chance.entity;

import com.bee32.plover.orm.util.StandardSamples;

public class ChanceStages
        extends StandardSamples {

    public final ChanceStage INIT = new ChanceStage(10, "INIT", "初始化");
    public final ChanceStage LAUNCHED = new ChanceStage(20, "LAUN", "初步沟通");
    public final ChanceStage MEETING = new ChanceStage(30, "MEET", "交涉中");
    public final ChanceStage QUOTED = new ChanceStage(40, "QUOT", "已经报价");
    public final ChanceStage PAYMENT = new ChanceStage(50, "PAYM", "合同付款洽谈");
    public final ChanceStage DONE = new ChanceStage(100, "DONE", "合同签订");
    public final ChanceStage TERMINATED = new ChanceStage(110, "TERM", "终止");

}
