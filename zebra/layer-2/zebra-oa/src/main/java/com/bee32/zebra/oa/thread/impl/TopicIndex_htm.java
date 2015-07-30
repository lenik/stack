package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.schema.impl.CategoryDefCriteria;
import net.bodz.lily.model.base.schema.impl.CategoryDefMapper;
import net.bodz.lily.model.base.schema.impl.PhaseDefCriteria;
import net.bodz.lily.model.base.schema.impl.PhaseDefMapper;

import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.sys.Schemas;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SectionDiv;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.stat.MonthTrends;
import com.bee32.zebra.tk.stat.ValueDistrib;
import com.bee32.zebra.tk.stat.impl.MonthTrendsMapper;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class TopicIndex_htm
        extends SlimIndex_htm<TopicIndex, Topic, TopicCriteria> {

    public TopicIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(TopicIndex.class);
        indexFields.parse("i*sa", "op", "beginDate", "endDate", "subject", "text", "category", "phase", "value");
    }

    @Override
    protected TopicCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        TopicMapper mapper = ctx.query(TopicMapper.class);
        TopicCriteria criteria = CriteriaBuilder.fromRequest(new TopicCriteria(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("分类", true, //
                ctx.query(CategoryDefMapper.class).filter(CategoryDefCriteria.forSchema(Schemas.OPPORTUNITY)), //
                "cat", criteria.categoryId, criteria.noCategory);
        criteria.categoryId = sw.getSelection1();
        criteria.noCategory = sw.isSelectNull();

        sw = switchers.entityOf("阶段", true, //
                ctx.query(PhaseDefMapper.class).filter(PhaseDefCriteria.forSchema(Schemas.OPPORTUNITY)), //
                "phase", criteria.phaseId, criteria.noPhase);
        criteria.phaseId = sw.getSelection1();
        criteria.noPhase = sw.isSelectNull();

        // HtmlDivTag valDiv = out.div().text("金额：");
        // 全部 1万以下 1-10万 10-100万 100-1000万 1000万以上");

        sw = switchers.entryOf("年份", true, //
                mapper.histoByYear(), "year", criteria.year, criteria.noYear);
        criteria.year = sw.getSelection1();
        criteria.noYear = sw.isSelectNull();

        return criteria;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Topic> a, IUiRef<TopicIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        TopicMapper mapper = ctx.query(TopicMapper.class);
        TopicCriteria criteria = ctx.query(TopicCriteria.class);
        List<Topic> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Topic o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                ref(tr.td(), o.getOp()).align("center");
                tr.td().text(fmt.formatDate(o.getBeginDate()));
                tr.td().text(fmt.formatDate(o.getEndDate()));
                itab.cocols("m", tr, o);
                ref(tr.td(), o.getCategory());
                ref(tr.td(), o.getPhase()).class_("small");
                tr.td().text(o.getValue());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

    @Override
    protected void sections(IHtmlViewContext ctx, IHtmlTag out, IUiRef<TopicIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        super.sections(ctx, out, ref, options);

        SectionDiv section;
        section = new SectionDiv(out, "s-stat", "统计/Statistics", IFontAwesomeCharAliases.FA_CALCULATOR);
        {
            IHtmlTag chart = section.contentDiv.div().id("monthvalsum").class_("plot");
            chart.p().text("近期累计的项目价值：");
            chart.div().class_("view").style("height: 15em");
            HtmlDivTag dataDiv = chart.div().class_("data");
            MonthTrendsMapper monthTrendsMapper = ctx.query(MonthTrendsMapper.class);
            StringBuffer sb = new StringBuffer(4096);
            for (MonthTrends row : monthTrendsMapper.sum1("topic", "creation", "val")) {
                sb.append(row.getYear());
                sb.append("-");
                sb.append(row.getMonth());
                sb.append("\t");
                sb.append(row.getSum1());
                sb.append(";");
            }
            dataDiv.text(sb);

            section.contentDiv.hr();

            chart = section.contentDiv.div().id("catdist").class_("plot");
            chart.p().text("项目类型分布：");
            chart.div().class_("view").style("height: 300px");
            dataDiv = chart.div().class_("data");
            TopicMapper topicMapper = ctx.query(TopicMapper.class);
            sb.setLength(0);
            for (ValueDistrib row : topicMapper.catDistrib()) {
                if (row == null)
                    continue;
                sb.append(row.getValueLabel());
                sb.append("\t");
                sb.append(row.getCount());
                sb.append(";");
            }
            dataDiv.text(sb);

            section.contentDiv.hr();

            chart = section.contentDiv.div().id("phasedist").class_("plot");
            chart.p().text("项目阶段分布：");
            chart.div().class_("view").style("height: 300px");
            dataDiv = chart.div().class_("data");
            sb.setLength(0);
            for (ValueDistrib row : topicMapper.phaseDistrib()) {
                if (row == null)
                    continue;
                sb.append(row.getValueLabel());
                sb.append("\t");
                sb.append(row.getCount());
                sb.append(";");
            }
            dataDiv.text(sb);
        }

        // "评论/Comments", IFontAwesomeCharAliases.FA_COMMENTS);
        // "调测信息/Debug", IFontAwesomeCharAliases.FA_BUG);
    }

}
