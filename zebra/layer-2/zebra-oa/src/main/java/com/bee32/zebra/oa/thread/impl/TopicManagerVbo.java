package com.bee32.zebra.oa.thread.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.c.string.Strings;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SectionDiv;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.CategoryDef;
import com.tinylily.model.base.schema.PhaseDef;
import com.tinylily.model.base.security.User;

public class TopicManagerVbo
        extends Zc3Template_CEM<TopicManager, Topic> {

// CategoryMana

    public TopicManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(TopicManager.class);
        formStruct = new Topic().getFormStruct();
        setIndexFields("id", "op", "subject", "text", "category", "phase", "value" //
                , "creationTime", "lastModified" //
        // , "owner.label", "ownerGroup.label"
        );
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<TopicManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        TopicManager manager = ref.get();
        TopicMapper mapper = manager.getMapper();
        List<Topic> list = mapper.all();

        titleInfo(p);

        SectionDiv section = new SectionDiv(p.mainCol, "s-filter", "过滤/Filter", IFontAwesomeCharAliases.FA_FILTER);
        HtmlDivTag filters = section.contentDiv;

        filters.div().text("分类 Classification：Apple Bar Cat Dog Earth Foo Goo");
        filters.div().text("阶段 Phase：Start Stopped Paused End");
        filters.div().text("金额：全部 1万以下 1-10万 10-100万 100-1000万 1000万以上");
        filters.div().text("年份：2010 2011 2012 2013 2014 更多");

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (Topic o : list) {
            User op = o.getOp();
            CategoryDef category = o.getCategory();
            PhaseDef phase = o.getPhase();

            HtmlTrTag tr = indexTable.tbody.tr();
            tr.td().text(o.getId()).class_("col-id");
            tr.td().text(op == null ? "" : op.getFullName()).align("center");
            tr.td().b().text(o.getSubject()).class_("small").style("max-width: 20em");
            tr.td().text(Strings.ellipsis(o.getText(), 50)).class_("small").style("max-width: 30em");
            tr.td().text(category == null ? "" : category.getLabel());

            tr.td().text(phase == null ? "" : phase.getLabel()).class_("small");
            tr.td().text(o.getValue());
            stdcols(tr, o);
        }

        dumpData(p.extradata, list);

        section = new SectionDiv(p.mainCol, "s-stat", "统计/Statistics", IFontAwesomeCharAliases.FA_CALCULATOR);
        section = new SectionDiv(p.mainCol, "s-bar", "图表/Charts", IFontAwesomeCharAliases.FA_BAR_CHART);
        section = new SectionDiv(p.mainCol, "s-line", "图表/Charts", IFontAwesomeCharAliases.FA_LINE_CHART);
        section = new SectionDiv(p.mainCol, "s-pie", "图表/Charts", IFontAwesomeCharAliases.FA_PIE_CHART);
        section = new SectionDiv(p.mainCol, "s-comments", "评论/Comments", IFontAwesomeCharAliases.FA_COMMENTS);
        section = new SectionDiv(p.mainCol, "s-debug", "调测信息/Debug", IFontAwesomeCharAliases.FA_BUG);

        return ctx;
    }

}
