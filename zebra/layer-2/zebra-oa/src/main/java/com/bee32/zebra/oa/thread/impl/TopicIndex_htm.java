package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.t.variant.VarMapState;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.base.schema.impl.CategoryDefMapper;
import net.bodz.lily.model.base.schema.impl.CategoryDefMask;
import net.bodz.lily.model.base.schema.impl.PhaseDefMapper;
import net.bodz.lily.model.base.schema.impl.PhaseDefMask;

import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.sys.Schemas;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SectionDiv_htm1;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.stat.MonthTrends;
import com.bee32.zebra.tk.stat.ValueDistrib;
import com.bee32.zebra.tk.stat.impl.MonthTrendsMapper;

public class TopicIndex_htm
        extends SlimIndex_htm<TopicIndex, Topic, TopicMask> {

    public TopicIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(TopicIndex.class);
        indexFields.parse("i*sa", "op", "beginDate", "endDate", "subject", "text", "category", "phase", "value");
    }

    @Override
    protected TopicMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        TopicMapper mapper = ctx.query(TopicMapper.class);
        TopicMask mask = VarMapState.restoreFrom(new TopicMask(), ctx.getRequest());

        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("分类", true, //
                ctx.query(CategoryDefMapper.class).filter(CategoryDefMask.forSchema(Schemas.OPPORTUNITY)), //
                "cat", mask.categoryId, mask.noCategory);
        mask.categoryId = sw.getSelection1();
        mask.noCategory = sw.isSelectNull();

        sw = switchers.entityOf("阶段", true, //
                ctx.query(PhaseDefMapper.class).filter(PhaseDefMask.forSchema(Schemas.OPPORTUNITY)), //
                "phase", mask.phaseId, mask.noPhase);
        mask.phaseId = sw.getSelection1();
        mask.noPhase = sw.isSelectNull();

        // HtmlDiv valDiv = out.div().text("金额：");
        // 全部 1万以下 1-10万 10-100万 100-1000万 1000万以上");

        sw = switchers.entryOf("年份", true, //
                mapper.histoByYear(), "year", mask.year, mask.noYear);
        mask.year = sw.getSelection1();
        mask.noYear = sw.isSelectNull();

        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<TopicIndex> ref)
            throws ViewBuilderException, IOException {
        TopicMapper mapper = ctx.query(TopicMapper.class);
        TopicMask mask = ctx.query(TopicMask.class);
        List<Topic> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (Topic o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            ref(tr.td().align("center"), o.getOp());
            tr.td().text(fmt.formatDate(o.getBeginDate()));
            tr.td().text(fmt.formatDate(o.getEndDate()));
            itab.cocols("m", tr, o);
            ref(tr.td(), o.getCategory());
            ref(tr.td().class_("small"), o.getPhase());
            tr.td().text(o.getValue());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

    @Override
    protected void sections(IHtmlViewContext ctx, IHtmlOut out, IUiRef<TopicIndex> ref)
            throws ViewBuilderException, IOException {
        super.sections(ctx, out, ref);

        out = new SectionDiv_htm1("统计/Statistics", IFontAwesomeCharAliases.FA_CALCULATOR).build(out, "s-stat");
        {
            IHtmlOut chart = out.div().id("monthvalsum").class_("plot");
            chart.p().text("近期累计的项目价值：");
            chart.div().class_("view").style("height: 15em");
            HtmlDiv dataDiv = chart.div().class_("data");
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

            out.hr();

            chart = out.div().id("catdist").class_("plot");
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

            out.hr();

            chart = out.div().id("phasedist").class_("plot");
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
