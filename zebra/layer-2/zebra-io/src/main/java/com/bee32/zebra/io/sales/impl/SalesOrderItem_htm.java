package com.bee32.zebra.io.sales.impl;

import java.io.IOException;
import java.util.Set;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.art.Artifact;
import com.bee32.zebra.io.sales.SalesOrderItem;
import com.bee32.zebra.tk.slim.SlimForm_htm;

public class SalesOrderItem_htm
        extends SlimForm_htm<SalesOrderItem> {

    public SalesOrderItem_htm() {
        super(SalesOrderItem.class);
    }

    @Override
    protected void selectFields(FieldDeclGroup group, Set<String> includes, Set<String> excludes)
            throws ViewBuilderException, IOException {
        excludes.add("codeName");
        excludes.add("label");
        excludes.add("description");
    }

    @Override
    protected Object persist(boolean create, IHtmlViewContext ctx, IHtmlOut out, IUiRef<SalesOrderItem> ref)
            throws Exception {
        SalesOrderItem item = ref.get();
        Artifact artifact = item.getArtifact();
        if (artifact.getId() == null)
            artifact.setId(0); // Reserved for "Unspecified".
        return super.persist(create, ctx, out, ref);
    }

}
