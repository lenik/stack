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
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.*;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.form.FieldDeclGroup;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.t.range.DateRange;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.calendar.LogEntry;
import com.bee32.zebra.oa.calendar.LogSelector;
import com.bee32.zebra.tk.site.IZebraSiteAnchors;
import com.bee32.zebra.tk.slim.SlimForm0_htm;
import com.tinylily.model.base.schema.CategoryDef;
import com.tinylily.model.base.schema.FormDef;
import com.tinylily.model.base.security.LoginContext;
import com.tinylily.model.base.security.User;

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

        Map<Integer, LogEntryGroup> dayLegs = new TreeMap<>();
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

        HtmlDivTag topbar = out.div();
        // topbar.a().class_("toggle").id("showall").text("显示所有");
        // topbar.input().type("checkbox").class_("toggle").idLabel("showall").text("显示所有");
        // topbar.button().type("button").class_("btn").dataToggle("button").text("显示所有");
        HtmlSpanTag showAllToggle = topbar.span().class_("btn-group btn-toggle").id("showall");
        showAllToggle.button().class_("btn btn-sm btn-primary active").text("选中").value("off");
        showAllToggle.button().class_("btn btn-sm btn-default").text("全部").value("on");

        topbar.text(date.toString("yyyy 年 M 月"));

        HtmlTableTag tab = out.table().align("center");
        {
            HtmlTrTag tr = tab.tr();

            String prevYear = date.minusYears(1).toString("YYYY-M");
            String prevMonth = date.minusMonths(1).toString("YYYY-M");
            String nextMonth = date.plusMonths(1).toString("YYYY-M");
            String nextYear = date.plusYears(1).toString("YYYY-M");
            HtmlTdTag prevTd = tr.td();

            prevTd.iText(FA_ANGLE_DOUBLE_LEFT, "fa");
            prevTd.a().href("../" + prevYear + "/").text("上一年");
            prevTd.iText(FA_ANGLE_LEFT, "fa");
            prevTd.a().href("../" + prevMonth + "/").text("上一月");
            prevTd.iText(FA_ANGLE_LEFT, "fa");

            buildMonthNav(tr.td(), date, dayLegs);

            HtmlTdTag nextTd = tr.td();
            nextTd.iText(FA_ANGLE_RIGHT, "fa");
            nextTd.a().href("../" + nextMonth + "/").text("下一月");
            nextTd.iText(FA_ANGLE_RIGHT, "fa");
            nextTd.a().href("../" + nextYear + "/").text("下一年");
            nextTd.iText(FA_ANGLE_DOUBLE_RIGHT, "fa");
        }

        out.hr();

        HtmlDivTag legsDiv = out.div().class_("zu-legs");
        for (Entry<Integer, LogEntryGroup> entry : dayLegs.entrySet()) {
            int day = entry.getKey();
            LogEntryGroup leg = entry.getValue();
            // DataTable dt = new DataTable(legsDiv);
            // dt.head.th().text("记录");
            // dt.head.th().text("操作人");
            // dt.head.th().text("分类");
            // dt.head.th().text("信息");
            // dt.head.th().text("日期");

            HtmlOlTag ol = legsDiv.ol().class_("zu-leg").id("log-" + day);

            for (LogEntry log : leg) {
                HtmlLiTag li = ol.li();

                HtmlSpanTag dateSpan = li.span().class_("date");
                DateFormat dateFormat = Dates.YYYY_MM_DD;
                dateSpan.text("[" + dateFormat.format(log.getDate()) + "]");
                li.text(" ");

                HtmlOlTag cats = li.ol().class_("breadcrumb cat");
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
                    HtmlSpanTag opSpan = li.span().class_("op");
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
