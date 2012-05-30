package com.bee32.sem.hr.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class JobPosts extends StandardSamples {

    public final JobPost generalManager = new JobPost("generalManager", "总经理");
    public final JobPost cashier = new JobPost("cashier", "出纳");
    public final JobPost bookkeeper = new JobPost("bookkeeper", "会计");
    public final JobPost salesManager = new JobPost("salesManager", "销售经理");
    public final JobPost salesman = new JobPost("salesman", "销售员");
    public final JobPost secretarial = new JobPost("secretarial", "文秘");
    public final JobPost technicalDirector = new JobPost("technicalDirector", "技术总监");
    public final JobPost servicePersonnel = new JobPost("servicePersonnel", "服务人员");
    public final JobPost maintenanceDirector = new JobPost("maintenanceDirector", "运维主管");
}
