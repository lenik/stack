package com.bee32.zebra.oa.contact.impl;

import java.util.ArrayList;
import java.util.List;

import net.bodz.bas.t.pojo.Pair;

public class PartyType {

    public static final int UNSET = 0;
    public static final int CUSTOMER = 1;
    public static final int SUPPLIER = 2;
    public static final int SHIPPER = 3;
    public static final int EMPLOYEE = 4;

    static List<Pair<Integer, String>> list;
    static {
        list = new ArrayList<>();
        list.add(Pair.of(UNSET, "未分类"));
        list.add(Pair.of(CUSTOMER, "客户"));
        list.add(Pair.of(SUPPLIER, "供应商"));
        list.add(Pair.of(SHIPPER, "承运人"));
        list.add(Pair.of(EMPLOYEE, "雇员"));
    }

}
