package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.ox1.dict.ShortNameDict;

/**
 * 机会来源
 */
@Entity
public class ChanceSourceType
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceSourceType() {
        super();
    }

    public ChanceSourceType(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceSourceType(String name, String label) {
        super(name, label);
    }

    public static ChanceSourceType TELEPHONE = new ChanceSourceType("TELE", "电话来访");
    public static ChanceSourceType INTRODUCTION = new ChanceSourceType("INTR", "客户介绍");
    public static ChanceSourceType DEVELOP = new ChanceSourceType("DEVE", "独立开发");
    public static ChanceSourceType MEDIA = new ChanceSourceType("MEDI", "媒体宣传");
    public static ChanceSourceType PROMOTION = new ChanceSourceType("PROM", "促销活动");
    public static ChanceSourceType CUSTOMER = new ChanceSourceType("CUST", "老客户");
    public static ChanceSourceType AGENT = new ChanceSourceType("AGEN", "代理商");
    public static ChanceSourceType PARTNER = new ChanceSourceType("PART", "合作伙伴");
    public static ChanceSourceType TENDER = new ChanceSourceType("TEND", "公开招标");
    public static ChanceSourceType INTERNET = new ChanceSourceType("INTE", "互联网");
    public static ChanceSourceType OTHER = new ChanceSourceType("OTHE", "其他");

}
