package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.Set;

import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;

import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class SalesOrderItemVbo
        extends SlimForm_htm<SalesOrderItem> {

    public SalesOrderItemVbo() {
        super(SalesOrderItem.class);
    }

    @Override
    protected void selectFields(FieldDeclGroup group, Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
        excludes.add("codeName");
        excludes.add("label");
        excludes.add("description");
    }

}
