package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.Set;

import net.bodz.bas.repr.viz.ViewBuilderException;

import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.sea.FooVbo;

public class SalesOrderItemVbo
        extends FooVbo<SalesOrderItem> {

    public SalesOrderItemVbo() {
        super(SalesOrderItem.class);
    }

    @Override
    protected void selectBasicFields(Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
        excludes.add("codeName");
        excludes.add("label");
        excludes.add("description");
    }

}
