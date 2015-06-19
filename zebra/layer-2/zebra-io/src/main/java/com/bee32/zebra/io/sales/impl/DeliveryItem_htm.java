package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.Set;

import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;

import com.bee32.zebra.io.sales.DeliveryItem;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class DeliveryItem_htm
        extends SlimForm_htm<DeliveryItem> {

    public DeliveryItem_htm() {
        super(DeliveryItem.class);
    }

    @Override
    protected void selectFields(FieldDeclGroup group, Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
        excludes.add("codeName");
        excludes.add("label");
        excludes.add("description");
        excludes.add("creationDate");
        excludes.add("lastModifiedDate");
        excludes.add("beginDate");
        excludes.add("endDate");
        excludes.add("version");
    }

}
