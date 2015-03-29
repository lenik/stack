package com.bee32.zebra.oa.calendar.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTime.Property;

import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlTableTag;
import net.bodz.bas.html.dom.tag.HtmlTbodyTag;
import net.bodz.bas.html.dom.tag.HtmlTdTag;
import net.bodz.bas.html.dom.tag.HtmlTheadTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.t.range.DateRange;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.calendar.LogEntry;
import com.bee32.zebra.oa.calendar.LogSelector;
import com.bee32.zebra.tk.hbin.DataTable;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.slim.SlimForm0_htm;
import com.tinylily.model.base.security.LoginContext;

public class LogSelector_htm
        extends SlimForm0_htm<LogSelector>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public LogSelector_htm() {
        super(LogSelector.class);
    }

    @Override
    protected IHtmlTag beforeForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<LogSelector> ref, IOptions options)
            throws ViewBuilderException, IOException {
        LogEntryMapper mapper = ctx.query(LogEntryMapper.class);
        LoginContext login = LoginContext.fromSession();
        int uid = login == null ? -1 : login.user.getId();

        LogSelector selector = ref.get();
        LogEntryCriteria criteria = new LogEntryCriteria();
        criteria.setDateRange(DateRange.minMax(selector.getStart(), selector.getEnd()));

        Map<Integer, LogEntryGroup> dayLegs = new HashMap<>();
        List<LogEntry> logs = mapper.filter(criteria);
        for (LogEntry log : logs) {
            int day = new DateTime(log.getDate()).getDayOfMonth();

            LogEntryGroup leg = dayLegs.get(day);
            if (leg == null)
                dayLegs.put(day, leg = new LogEntryGroup());
            leg.add(log);

            if (log.getUserId() == uid)
                leg.addIcon(FA_USER);
        }

        for (LogEntryGroup leg : dayLegs.values())
            leg.sort();

        DateTime date = new DateTime(selector.getStart());
        HtmlTableTag tab = out.table().align("center");
        {
            HtmlTrTag tr = tab.tr();

            String prevMonth = date.minusMonths(1).toString("YYYY-M");
            String nextMonth = date.plusMonths(1).toString("YYYY-M");
            HtmlTdTag prevTd = tr.td();
            prevTd.iText(FA_ANGLE_LEFT, "fa");
            prevTd.a().href("../" + prevMonth + "/").text("上一月");

            buildMonthNav(tr.td(), date, dayLegs);

            HtmlTdTag nextTd = tr.td();
            nextTd.a().href("../" + nextMonth + "/").text("下一月");
            nextTd.iText(FA_ANGLE_RIGHT, "fa");
        }

        HtmlDivTag legsDiv = out.div().id("zp-legs");
        for (LogEntryGroup leg : dayLegs.values()) {
            DataTable dt = new DataTable(legsDiv);

            dt.head.th().text("记录");
            dt.head.th().text("操作人");
            dt.head.th().text("分类");
            dt.head.th().text("信息");
            dt.head.th().text("日期");

            for (LogEntry log : leg) {
                HtmlTrTag tr = dt.body.tr();
                tr.td().text(log.getSource() + log.getId());
                tr.td().text(log.getOp().getLabel());
                tr.td().text(log.getForm().getLabel() + log.getCategory().getLabel());
                tr.td().text(log.getMessage());
                tr.td().text(log.getDate());
            }
        }
        return out;
    }

    // String[] weekDayNames = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", };
    String[] weekDayNames = { "日", "一", "二", "三", "四", "五", "六", };

    void buildMonthNav(IHtmlTag out, DateTime date, Map<Integer, LogEntryGroup> dayLegs) {
        HtmlTableTag tab = out.table().class_("zu-calendar");
        tab.attr("data-year", date.getYear());
        tab.attr("data-month", date.getMonthOfYear());

        Property dayOfMonth = date.dayOfMonth();
        int activeDay = -1;
        {
            DateTime today = new DateTime();
            if (date.getYear() == today.getYear() && date.getMonthOfYear() == today.getMonthOfYear())
                activeDay = today.getDayOfMonth();
        }

        int minOffset = dayOfMonth.withMinimumValue().dayOfWeek().get();
        int maxOffset = dayOfMonth.withMaximumValue().dayOfWeek().get();

        HtmlTheadTag thead = tab.thead();
        for (int wday = 1; wday <= 7; wday++) {
            thead.th().text(weekDayNames[wday % 7]);
        }

        HtmlTbodyTag tbody = tab.tbody();
        HtmlTrTag tr = tbody.tr();
        for (int wday = 1; wday < minOffset; wday++)
            tr.td().class_("placeholder");

        int moSize = dayOfMonth.getMaximumValue();
        for (int day = 1; day <= moSize; day++) {
            LogEntryGroup leg = dayLegs.get(day);

            int wday = (day - 1 + minOffset) % 7;
            List<String> classes = new ArrayList<>();
            if (day == activeDay)
                classes.add("active");
            if (leg == null || leg.isEmpty())
                classes.add("trivial");
            else
                classes.add("log");

            HtmlTdTag td = tr.td().class_(StringArray.join(" ", classes));
            td.attr("data-day", day);

            if (leg != null) {
                IHtmlTag dayOut = td.div();
                if (leg.isBold())
                    dayOut = dayOut.b();
                if (leg.isItalics())
                    dayOut = dayOut.i();
                dayOut.text(day);

                List<String> icons = leg.getIcons();
                if (icons != null)
                    for (String icon : icons)
                        td.iText(icon, "fa");

                String inlineText = leg.getInlineText();
                if (inlineText != null)
                    td.text(inlineText);
            } else {
                td.text(day);
            }

            if (wday == 0)
                tr = tbody.tr();
        }

        for (int wday = maxOffset; wday < 7; wday++)
            tr.td().class_("placeholder");
    }

    @Override
    protected IHtmlTag afterForm(IHttpViewContext ctx, IHtmlTag out, IUiRef<LogSelector> ref, IOptions options)
            throws ViewBuilderException, IOException {
        return out;
    }

    @Override
    protected boolean overrideFieldGroup(IHttpViewContext ctx, IHtmlTag out, IUiRef<?> instanceRef,
            FieldDeclGroup group, IOptions options)
            throws ViewBuilderException, IOException {
        return true;
    }

}
