package com.bee32.zebra.oa.calendar.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.DateTime.Property;

import net.bodz.bas.c.java.util.Dates;
import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.*;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.t.range.DateRange;
import net.bodz.bas.t.variant.VarMapState;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.schema.CategoryDef;
import net.bodz.lily.model.base.schema.FormDef;
import net.bodz.lily.model.base.security.LoginContext;
import net.bodz.lily.model.base.security.User;

import com.bee32.zebra.oa.calendar.LogEntry;
import com.bee32.zebra.oa.calendar.LogSelector;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.slim.SlimForm0_htm;

public class LogSelector_htm
        extends SlimForm0_htm<LogSelector>
        implements IZebraSiteAnchors, IFontAwesomeCharAliases {

    public LogSelector_htm() {
        super(LogSelector.class);
    }

    @Override
    protected IHtmlOut beforeForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<LogSelector> ref)
            throws ViewBuilderException, IOException {
        LogEntryMapper mapper = ctx.query(LogEntryMapper.class);
        if (mapper == null)
            throw new NullPointerException("mapper");
        LoginContext login = LoginContext.fromSession();
        int uid = login == null ? -1 : login.user.getId();

        LogSelector selector = ref.get();
        LogEntryMask mask = VarMapState.restoreFrom(new LogEntryMask(), ctx.getRequest());
        mask.setDateRange(DateRange.minMax(selector.getStart(), selector.getEnd()));

        Map<Integer, LogEntryGroup> dayLegs = new TreeMap<>();
        List<LogEntry> logs = mapper.filter(mask);
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

        HtmlDiv topbar = out.div();
        // topbar.a().class_("toggle").id("showall").text("显示所有");
        // topbar.input().type("checkbox").class_("toggle").idLabel("showall").text("显示所有");
        // topbar.button().type("button").class_("btn").dataToggle("button").text("显示所有");
        HtmlSpan showAllToggle = topbar.span().class_("btn-group btn-toggle").id("showall");
        showAllToggle.button().class_("btn btn-sm btn-primary active").text("选中").value("off");
        showAllToggle.button().class_("btn btn-sm btn-default").text("全部").value("on");

        topbar.text(date.toString("yyyy 年 M 月"));

        HtmlTable tab = out.table().align("center");
        {
            HtmlTr tr = tab.tr();

            String prevYear = date.minusYears(1).toString("YYYY-M");
            String prevMonth = date.minusMonths(1).toString("YYYY-M");
            String nextMonth = date.plusMonths(1).toString("YYYY-M");
            String nextYear = date.plusYears(1).toString("YYYY-M");
            HtmlTd prevTd = tr.td();

            prevTd.iText(FA_ANGLE_DOUBLE_LEFT, "fa");
            prevTd.a().href("../" + prevYear + "/").text("上一年");
            prevTd.iText(FA_ANGLE_LEFT, "fa");
            prevTd.a().href("../" + prevMonth + "/").text("上一月");
            prevTd.iText(FA_ANGLE_LEFT, "fa");

            buildMonthNav(tr.td(), date, dayLegs);

            HtmlTd nextTd = tr.td();
            nextTd.iText(FA_ANGLE_RIGHT, "fa");
            nextTd.a().href("../" + nextMonth + "/").text("下一月");
            nextTd.iText(FA_ANGLE_RIGHT, "fa");
            nextTd.a().href("../" + nextYear + "/").text("下一年");
            nextTd.iText(FA_ANGLE_DOUBLE_RIGHT, "fa");
        }

        out.hr();

        HtmlDiv legsDiv = out.div().class_("zu-legs");
        for (Entry<Integer, LogEntryGroup> entry : dayLegs.entrySet()) {
            int day = entry.getKey();
            LogEntryGroup leg = entry.getValue();
            // DataTable dt = new DataTable(legsDiv);
            // dt.head.th().text("记录");
            // dt.head.th().text("操作人");
            // dt.head.th().text("分类");
            // dt.head.th().text("信息");
            // dt.head.th().text("日期");

            HtmlOl ol = legsDiv.ol().class_("zu-leg").id("log-" + day);

            for (LogEntry log : leg) {
                HtmlLi li = ol.li();

                HtmlSpan dateSpan = li.span().class_("date");
                DateFormat dateFormat = Dates.YYYY_MM_DD;
                dateSpan.text("[" + dateFormat.format(log.getDate()) + "]");
                li.text(" ");

                HtmlOl cats = li.ol().class_("breadcrumb cat");
                cats.li().text(log.getMetadata().getLabel());

                FormDef form = log.getForm();
                if (form != null)
                    cats.li().text(form.getLabel());

                CategoryDef category = log.getCategory();
                if (category != null)
                    cats.li().text(category.getLabel());

                String href = _webApp_ + log.getMetadata().getName() + "/" + log.getId() + "/";
                li.a().class_("refid").href("_blank", href).iText(FA_INFO, "fa").text(log.getId());
                li.text(": ");
                li.span().class_("msg").text(log.getMessage());

                User op = log.getOp();
                if (op != null) {
                    li.text(" ");
                    HtmlSpan opSpan = li.span().class_("op");
                    // int id = op.getId();
                    String label = op.getLabel();
                    opSpan.text("@");
                    // HtmlATag a = opSpan.a().href(_webApp_+"mail/...");
                    opSpan.text(label);
                }
            }
        }
        return out;
    }

    // String[] weekDayNames = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", };
    String[] weekDayNames = { "日", "一", "二", "三", "四", "五", "六", };

    void buildMonthNav(IHtmlOut out, DateTime date, Map<Integer, LogEntryGroup> dayLegs) {
        HtmlTable tab = out.table().class_("zu-calendar");
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

        HtmlThead thead = tab.thead();
        for (int wday = 1; wday <= 7; wday++) {
            thead.th().text(weekDayNames[wday % 7]);
        }

        HtmlTbody tbody = tab.tbody();
        HtmlTr tr = tbody.tr();
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

            HtmlTd td = tr.td().class_(StringArray.join(" ", classes));
            td.attr("data-day", day);

            if (leg != null) {
                IHtmlOut dayOut = td.div();
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
    protected IHtmlOut afterForm(IHtmlViewContext ctx, IHtmlOut out, IUiRef<LogSelector> ref)
            throws ViewBuilderException, IOException {
        return out;
    }

    @Override
    protected boolean overrideFieldGroup(IHtmlViewContext ctx, IHtmlOut out, IUiRef<?> instanceRef, FieldDeclGroup group)
            throws ViewBuilderException, IOException {
        return true;
    }

}
