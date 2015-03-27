package com.bee32.zebra.erp.site;

import java.io.IOException;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.site.OaSite;
import com.bee32.zebra.oa.site.OaSiteVbo;

public class ErpSiteVbo
        extends OaSiteVbo {

    @Override
    public IHtmlTag buildHtmlView(IHttpViewContext ctx, IHtmlTag out, IUiRef<OaSite> ref, IOptions options)
            throws ViewBuilderException, IOException {
        out = super.buildHtmlView(ctx, out, ref, options);
        return out;
    }

    @Override
    protected void defaultBody(IHtmlTag out, OaSite site) {
        super.defaultBody(out, site);
    }

}
