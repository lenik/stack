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
import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;

public class DiaryIndexVbo
        extends Zc3Template_CEM<DiaryIndex, Diary> {

    public DiaryIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(DiaryIndex.class);
        insertIndexFields("i*sa", "op", "beginDate", "subject", "text");
    }

    @Override
    public void dataIndex(IHtmlViewContext ctx, DataViewAnchors<Diary> a, IUiRef<DiaryIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        DiaryMapper mapper = ctx.query(DiaryMapper.class);

        DiaryCriteria criteria = criteriaFromRequest(new DiaryCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(a.frame, "s-filter");
        {
            SwitchOverride<Integer> so;
            so = filters.switchPairs("年份", true, //
                    mapper.histoByYear(), "year", criteria.year, criteria.noYear);
            criteria.year = so.key;
            criteria.noYear = so.isNull;
        }

        List<Diary> list = postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (Diary o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                ref(tr.td(), o.getOp()).align("center");
                tr.td().text(fn.formatDate(o.getBeginDate()));
                tr.td().text(fn.formatDate(o.getEndDate()));
                cocols("m", tr, o);
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
