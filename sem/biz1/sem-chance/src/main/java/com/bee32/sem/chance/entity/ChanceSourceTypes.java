package com.bee32.sem.chance.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class ChanceSourceTypes
        extends StandardSamples {

    public final ChanceSourceType TELEPHONE = new ChanceSourceType("TELE", "电话来访");
    public final ChanceSourceType INTRODUCTION = new ChanceSourceType("INTR", "客户介绍");
    public final ChanceSourceType DEVELOP = new ChanceSourceType("DEVE", "独立开发");
    public final ChanceSourceType MEDIA = new ChanceSourceType("MEDI", "媒体宣传");
    public final ChanceSourceType PROMOTION = new ChanceSourceType("PROM", "促销活动");
    public final ChanceSourceType CUSTOMER = new ChanceSourceType("CUST", "老客户");
    public final ChanceSourceType AGENT = new ChanceSourceType("AGEN", "代理商");
    public final ChanceSourceType PARTNER = new ChanceSourceType("PART", "合作伙伴");
    public final ChanceSourceType TENDER = new ChanceSourceType("TEND", "公开招标");
    public final ChanceSourceType INTERNET = new ChanceSourceType("INTE", "互联网");
    public final ChanceSourceType OTHER = new ChanceSourceType("OTHE", "其他");

}
