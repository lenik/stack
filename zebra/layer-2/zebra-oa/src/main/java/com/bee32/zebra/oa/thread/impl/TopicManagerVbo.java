package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.sys.Schemas;
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SectionDiv;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.impl.CategoryDefCriteria;
import com.tinylily.model.base.schema.impl.CategoryDefMapper;
import com.tinylily.model.base.schema.impl.PhaseDefCriteria;
import com.tinylily.model.base.schema.impl.PhaseDefMapper;

public class TopicManagerVbo
        extends Zc3Template_CEM<TopicManager, Topic> {

    boolean extensions;

    public TopicManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(TopicManager.class);
        formDef = new Topic().getFormDef();
        insertIndexFields("i*sa", "op", "beginTime", "endTime", "subject", "text", "category", "phase", "value");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<TopicManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        TopicMapper mapper = ctx.query(TopicMapper.class);

        TopicCriteria criteria = criteriaFromRequest(new TopicCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(p.mainCol, "s-filter");
        {
            SwitchOverride<Integer> so;
            so = filters.switchEntity("分类", true, //
                    ctx.query(CategoryDefMapper.class).filter(CategoryDefCriteria.forSchema(Schemas.OPPORTUNITY)), //
                    "cat", criteria.categoryId, criteria.noCategory);
            criteria.categoryId = so.key;
            criteria.noCategory = so.isNull;

            so = filters.switchEntity("阶段", true, //
                    ctx.query(PhaseDefMapper.class).filter(PhaseDefCriteria.forSchema(Schemas.OPPORTUNITY)), //
                    "phase", criteria.phaseId, criteria.noPhase);
            criteria.phaseId = so.key;
            criteria.noPhase = so.isNull;

            // HtmlDivTag valDiv = out.div().text("金额：");
            // 全部 1万以下 1-10万 10-100万 100-1000万 1000万以上");

            so = filters.switchPairs("年份", false, //
                    mapper.histoByYear(), "year", criteria.year, criteria.noYear);
            criteria.year = so.key;
            criteria.noYear = so.isNull;
        }

        List<Topic> list = postfilt(mapper.filter(criteria));
        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (Topic o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            ref(tr.td(), o.getOp()).align("center");
            tr.td().text(fn.formatDate(o.getBeginTime()));
            tr.td().text(fn.formatDate(o.getEndTime()));
            cocols("m", tr, o);
            ref(tr.td(), o.getCategory());
            ref(tr.td(), o.getPhase()).class_("small");
            tr.td().text(o.getValue());
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        if (extensions) {
            SectionDiv section;
            section = new SectionDiv(p.mainCol, "s-stat", "统计/Statistics", IFontAwesomeCharAliases.FA_CALCULATOR);
            section = new SectionDiv(p.mainCol, "s-bar", "图表/Charts", IFontAwesomeCharAliases.FA_BAR_CHART);
            section = new SectionDiv(p.mainCol, "s-line", "图表/Charts", IFontAwesomeCharAliases.FA_LINE_CHART);
            section = new SectionDiv(p.mainCol, "s-pie", "图表/Charts", IFontAwesomeCharAliases.FA_PIE_CHART);
            section = new SectionDiv(p.mainCol, "s-comments", "评论/Comments", IFontAwesomeCharAliases.FA_COMMENTS);
            section = new SectionDiv(p.mainCol, "s-debug", "调测信息/Debug", IFontAwesomeCharAliases.FA_BUG);
        }

        return ctx;
    }

}
