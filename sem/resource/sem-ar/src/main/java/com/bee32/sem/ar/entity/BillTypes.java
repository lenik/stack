package com.bee32.sem.ar.entity;

import com.bee32.plover.orm.sample.StandardSamples;

public class BillTypes
        extends StandardSamples {

    public final BillType TELEPHONE = new BillType("BANK", "银行承兑汇票");
    public final BillType TALK = new BillType("BUSS", "商业承兑汇票");

}
