package com.bee32.zebra.io.sales.impl;

import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.sea.FooVbo;

public class SalesOrderItemVbo
        extends FooVbo<SalesOrderItem> {

    public SalesOrderItemVbo() {
        super(SalesOrderItem.class);
    }

}
