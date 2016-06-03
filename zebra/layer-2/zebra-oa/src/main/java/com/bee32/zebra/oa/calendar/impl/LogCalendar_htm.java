package com.bee32.zebra.oa.calendar.impl;

import java.io.IOException;

import net.bodz.bas.c.java.util.Dates;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.calendar.LogCalendar;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.slim.SlimSplit_htm;

public class LogCalendar_htm
        extends SlimSplit_htm<LogCalendar>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public LogCalendar_htm() {
        super(LogCalendar.class);
    }

    @Override
    protected void buildBody(IHtmlViewContext ctx, IHtmlOut out, IUiRef<LogCalendar> ref)
            throws ViewBuilderException, IOException {
        if (fn.redirect.addSlash(ctx, ref))
            return;
        String thisMonth = Dates.YYYY_M.format(System.currentTimeMillis());
        ctx.getResponse().sendRedirect(thisMonth + "/");
        ctx.stop();
    }

}
