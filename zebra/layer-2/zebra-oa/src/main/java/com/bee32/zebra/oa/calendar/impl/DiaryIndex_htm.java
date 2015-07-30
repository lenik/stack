package com.bee32.zebra.oa.calendar.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.oa.calendar.Diary;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class DiaryIndex_htm
        extends SlimIndex_htm<DiaryIndex, Diary, DiaryCriteria> {

    public DiaryIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(DiaryIndex.class);
        indexFields.parse("i*sa", "op", "beginDate", "endDate", "subject", "text");
    }

    @Override
    protected DiaryCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        DiaryMapper mapper = ctx.query(DiaryMapper.class);
        DiaryCriteria criteria = CriteriaBuilder.fromRequest(new DiaryCriteria(), ctx.getRequest());
        SwitcherModel<Integer> sw;
        sw = switchers.entryOf("年份", true, //
                mapper.histoByYear(), "year", criteria.year, criteria.noYear);
        criteria.year = sw.getSelection1();
        criteria.noYear = sw.isSelectNull();
        return criteria;
    }

    @Override
    public void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Diary> a, IUiRef<DiaryIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        DiaryMapper mapper = ctx.query(DiaryMapper.class);
        DiaryCriteria criteria = ctx.query(DiaryCriteria.class);
        List<Diary> list = postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (Diary o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                ref(tr.td(), o.getOp()).align("center");
                tr.td().text(fmt.formatDate(o.getBeginDate()));
                tr.td().text(fmt.formatDate(o.getEndDate()));
                itab.cocols("m", tr, o);
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
