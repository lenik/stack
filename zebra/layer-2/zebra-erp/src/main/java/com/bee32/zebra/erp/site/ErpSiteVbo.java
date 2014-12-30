package com.bee32.zebra.erp.site;

import java.io.IOException;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.site.vhost.CurrentVirtualHost;
import net.bodz.bas.site.vhost.IVirtualHost;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.site.OaSite;
import com.bee32.zebra.oa.site.OaSiteVbo;

public class ErpSiteVbo
        extends OaSiteVbo {

    @Override
    public IHtmlTag buildHtmlView(IHtmlViewContext ctx, IHtmlTag out, IUiRef<OaSite> ref, IOptions options)
            throws ViewBuilderException, IOException {
        out = super.buildHtmlView(ctx, out, ref, options);
        return out;
    }

    @Override
    protected void indexBody(IHtmlTag out, OaSite site) {
        out.h1().text("ERP Site Body");

        IVirtualHost vhost = CurrentVirtualHost.getVirtualHost();
        if (vhost == null)
            out.text("No vhost.");
        else
            out.text(vhost.getName());
    }

}
