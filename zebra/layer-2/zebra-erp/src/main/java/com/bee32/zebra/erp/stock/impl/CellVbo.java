package com.bee32.zebra.erp.stock.impl;

import java.io.IOException;

import net.bodz.bas.html.AbstractHtmlViewBuilder;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.site.IBasicSiteAnchors;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.erp.stock.Place;

public class CellVbo
        extends AbstractHtmlViewBuilder<Place>
        implements IBasicSiteAnchors {
    
    public CellVbo() {
        super(Place.class);
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<Place> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return ctx;
    }

}
