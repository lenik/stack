package com.bee32.sem.salary.entity;

import java.math.BigDecimal;

import com.bee32.plover.orm.sample.StandardSamples;

public class BaseBonuses
        extends StandardSamples {

    /**
     * Perfect Present.
     */
    public final BaseBonus PP = new BaseBonus("全勤奖", new BigDecimal(300));

    public final BaseBonus FUEL = new BaseBonus("油费补贴", new BigDecimal(300));
    public final BaseBonus LUNCH = new BaseBonus("午餐补贴", new BigDecimal(200));
    public final BaseBonus SUPPER = new BaseBonus("晚餐补贴", new BigDecimal(0));

}
