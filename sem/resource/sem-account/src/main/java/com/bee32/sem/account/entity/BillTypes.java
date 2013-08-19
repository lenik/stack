package com.bee32.sem.account.entity;

import com.bee32.plover.orm.sample.StandardSamples;

/**
 * 票据类型样本
 *
 * <p lang="en">
 */
public class BillTypes
        extends StandardSamples {

    public final BillType BANK = new BillType("BANK", "银行承兑汇票");
    public final BillType COMMERCIAL = new BillType("COMM", "商业承兑汇票");

}
