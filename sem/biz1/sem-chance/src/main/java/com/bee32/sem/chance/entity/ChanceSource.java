package com.bee32.sem.chance.entity;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.ShortNameDict;

/**
 * 机会来源
 */
@Entity
public class ChanceSource
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public ChanceSource() {
        super();
    }

    public ChanceSource(String name, String label, String description) {
        super(name, label, description);
    }

    public ChanceSource(String name, String label) {
        super(name, label);
    }

    public static ChanceSource TELEPHONE = new ChanceSource("TELE", "电话来访");

    public static ChanceSource INTRODUCTION = new ChanceSource("INTR", "客户介绍");

    public static ChanceSource DEVELOP = new ChanceSource("DEVE", "独立开发");

    public static ChanceSource MEDIA = new ChanceSource("MEDI", "媒体宣传");

    public static ChanceSource PROMOTION = new ChanceSource("PROM", "促销活动");

    public static ChanceSource CUSTOMER = new ChanceSource("CUST", "老客户");

    public static ChanceSource AGENT = new ChanceSource("AGEN", "代理商");

    public static ChanceSource PARTNER = new ChanceSource("PART", "合作伙伴");

    public static ChanceSource TENDER = new ChanceSource("TEND", "公开招标");

    public static ChanceSource INTERNET = new ChanceSource("INTE", "互联网");

    public static ChanceSource OTHER = new ChanceSource("OTHE", "其他");
}
