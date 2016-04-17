package com.bee32.zebra.erp.site;

import java.io.IOException;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.site.OaSite;
import com.bee32.zebra.oa.site.OaSite_htm;

public class ErpSite_htm
        extends OaSite_htm {

    @Override
    public IHtmlOut buildHtmlViewStart(IHtmlViewContext ctx, IHtmlOut out, IUiRef<OaSite> ref)
            throws ViewBuilderException, IOException {
        return super.buildHtmlViewStart(ctx, out, ref);
    }

    @Override
    protected void defaultBody(IHtmlOut out, OaSite site) {
        super.defaultBody(out, site);
    }

}
