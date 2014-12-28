package com.bee32.zebra.io.sales.impl;

import java.io.IOException;

import net.bodz.bas.html.AbstractHtmlViewBuilder;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.site.IBasicSiteAnchors;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.io.sales.Delivery;

public class DeliveryVbo
        extends AbstractHtmlViewBuilder<Delivery>
        implements IBasicSiteAnchors {
    
    public DeliveryVbo() {
        super(Delivery.class);
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<Delivery> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return ctx;
    }

}
