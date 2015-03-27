package com.bee32.zebra.oa.calendar.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.t.range.DateRange;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.calendar.LogEntry;
import com.bee32.zebra.oa.calendar.LogRange;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.slim.SlimForm0_htm;

public class LogRange_htm
        extends SlimForm0_htm<LogRange>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public LogRange_htm() {
        super(LogRange.class);
    }

    @Override
    protected IHtmlTag beforeForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<LogRange> ref, IOptions options)
            throws ViewBuilderException, IOException {

        LogRange range = ref.get();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(range.getStart());
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        

        LogEntryCriteria criteria = new LogEntryCriteria();
        criteria.setDateRange(DateRange.minMax(range.getStart(), range.getEnd()));

        LogEntryMapper mapper = ctx.query(LogEntryMapper.class);
        List<LogEntry> logs = mapper.filter(criteria);
        for (LogEntry log : logs) {
            out.div().text(log.getDate() + ": " + log.getMessage());
        }

        return out;
    }

    @Override
    protected IHtmlTag afterForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<LogRange> ref, IOptions options)
            throws ViewBuilderException, IOException {
        LogRange range = ref.get();
        return out;
    }

}
