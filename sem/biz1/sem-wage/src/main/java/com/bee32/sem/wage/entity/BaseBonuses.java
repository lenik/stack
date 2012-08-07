package com.bee32.sem.wage.entity;

import java.math.BigDecimal;

import com.bee32.plover.orm.sample.StandardSamples;

public class BaseBonuses
        extends StandardSamples {
    public final BaseBonus BASE = new BaseBonus("base", "基本日工资", true, new BigDecimal(90));
    public final BaseBonus ATTA = new BaseBonus("atta", "全勤奖", true, new BigDecimal(300));
    public final BaseBonus FUEL = new BaseBonus("fuel", "油费补贴", true, new BigDecimal(300));
    public final BaseBonus LATE = new BaseBonus("late", "迟到", false, new BigDecimal(10));
    public final BaseBonus POST = new BaseBonus("post", "岗位补贴", true, new BigDecimal(100));
    public final BaseBonus LUNCH = new BaseBonus("lunch", "中餐补", true, new BigDecimal(3));
    public final BaseBonus SUPPER = new BaseBonus("supper", "晚餐补", true, new BigDecimal(3));
    public final BaseBonus TRIP = new BaseBonus("trip", "出差补贴", true, new BigDecimal(20));

}
