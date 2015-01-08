package com.tinylily.model.base.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.FilterSectionDiv;
import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.TagDef;

public class TagDefIndexVbo
        extends Zc3Template_CEM<TagDefIndex, TagDef> {

    public TagDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(TagDefIndex.class);
        insertIndexFields("i*sa", "tagSet", "code", "label", "description", "refCount");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, DataViewAnchors<TagDef> a, IUiRef<TagDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        TagDefMapper mapper = ctx.query(TagDefMapper.class);

        TagDefCriteria criteria = criteriaFromRequest(new TagDefCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(a.frame, "s-filter");
        {
            SwitchOverride<Integer> so;
            so = filters.switchEntity("向量", false, //
                    ctx.query(TagSetDefMapper.class).all(), //
                    "tagv", criteria.tagSetId, false);
            criteria.tagSetId = so.key;
        }

        List<TagDef> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(ctx, a.data, "list");
        if (a.dataList())
            for (TagDef o : list) {
                HtmlTrTag tr = indexTable.tbody.tr();
                cocols("i", tr, o);
                tr.td().text(o.getTagSet().getLabel());
                cocols("cu", tr, o);
                tr.td().text(o.getRefCount());
                cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
