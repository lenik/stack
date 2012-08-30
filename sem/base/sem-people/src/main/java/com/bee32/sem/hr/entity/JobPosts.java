package com.bee32.sem.hr.entity;

import java.math.BigDecimal;

import com.bee32.plover.orm.sample.NormalSamples;

public class JobPosts
        extends NormalSamples {

    public final JobPost generalManager = new JobPost("总经理", new BigDecimal(2000));
    public final JobPost cashier = new JobPost("出纳", new BigDecimal(100));
    public final JobPost bookkeeper = new JobPost("会计", new BigDecimal(200));
    public final JobPost salesManager = new JobPost("销售经理", new BigDecimal(1000));
    public final JobPost salesman = new JobPost("销售员", new BigDecimal(100));
    public final JobPost secretarial = new JobPost("文秘", new BigDecimal(100));
    public final JobPost technicalDirector = new JobPost("技术总监", new BigDecimal(500));
    public final JobPost servicePersonnel = new JobPost("服务人员", new BigDecimal(100));
    public final JobPost maintenanceDirector = new JobPost("运维主管", new BigDecimal(300));
}
