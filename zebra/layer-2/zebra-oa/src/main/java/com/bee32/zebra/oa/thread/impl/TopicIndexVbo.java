package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.sys.Schemas;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SectionDiv;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.tinylily.model.base.schema.impl.CategoryDefCriteria;
import com.tinylily.model.base.schema.impl.CategoryDefMapper;
import com.tinylily.model.base.schema.impl.PhaseDefCriteria;
import com.tinylily.model.base.schema.impl.PhaseDefMapper;

public class TopicIndexVbo
        extends SlimIndex_htm<TopicIndex, Topic, TopicCriteria> {

    boolean extensions;

    public TopicIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(TopicIndex.class);
        indexFields.parse("i*sa", "op", "beginDate", "endDate", "subject", "text", "category", "phase", "value");
    }

    @Override
    protected TopicCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        TopicMapper mapper = ctx.query(TopicMapper.class);
        TopicCriteria criteria = fn.criteriaFromRequest(new TopicCriteria(), ctx.getRequest());

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
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<Topic> a, IUiRef<TopicIndex> ref, IOptions options)
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

        if (extensions) {
            SectionDiv section;
            section = new SectionDiv(a.frame, "s-stat", "统计/Statistics", IFontAwesomeCharAliases.FA_CALCULATOR);
            section = new SectionDiv(a.frame, "s-bar", "图表/Charts", IFontAwesomeCharAliases.FA_BAR_CHART);
            section = new SectionDiv(a.frame, "s-line", "图表/Charts", IFontAwesomeCharAliases.FA_LINE_CHART);
            section = new SectionDiv(a.frame, "s-pie", "图表/Charts", IFontAwesomeCharAliases.FA_PIE_CHART);
            section = new SectionDiv(a.frame, "s-comments", "评论/Comments", IFontAwesomeCharAliases.FA_COMMENTS);
            section = new SectionDiv(a.frame, "s-debug", "调测信息/Debug", IFontAwesomeCharAliases.FA_BUG);
        }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
