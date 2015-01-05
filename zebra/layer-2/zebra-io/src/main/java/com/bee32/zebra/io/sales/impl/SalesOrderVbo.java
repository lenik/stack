package com.bee32.zebra.io.sales.impl;

import com.bee32.zebra.io.sales.SalesOrder;
import com.bee32.zebra.tk.sea.FooMesgVbo;

public class SalesOrderVbo
        extends FooMesgVbo<SalesOrder> {

    public SalesOrderVbo() {
        super(SalesOrder.class);
    }

}
