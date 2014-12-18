package com.bee32.zebra.erp.stock.impl;

import java.io.IOException;

import net.bodz.bas.html.AbstractHtmlViewBuilder;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.site.IBasicSiteAnchors;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.erp.stock.StockEntry;

public class StockEntryVbo
        extends AbstractHtmlViewBuilder<StockEntry>
        implements IBasicSiteAnchors {
    
    public StockEntryVbo() {
        super(StockEntry.class);
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<StockEntry> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return ctx;
    }

}
