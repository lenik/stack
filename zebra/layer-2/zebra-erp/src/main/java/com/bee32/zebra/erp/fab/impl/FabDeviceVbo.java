package com.bee32.zebra.erp.fab.impl;

import java.io.IOException;

import net.bodz.bas.html.AbstractHtmlViewBuilder;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.site.IBasicSiteAnchors;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.erp.fab.FabDevice;

public class FabDeviceVbo
        extends AbstractHtmlViewBuilder<FabDevice>
        implements IBasicSiteAnchors {
    
    public FabDeviceVbo() {
        super(FabDevice.class);
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<FabDevice> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return ctx;
    }

}
