package com.bee32.zebra.sys.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHttpViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;
import com.tinylily.model.base.schema.TagDef;
import com.tinylily.model.base.schema.impl.TagDefCriteria;
import com.tinylily.model.base.schema.impl.TagDefMapper;
import com.tinylily.model.base.schema.impl.TagSetDefMapper;

public class TagDefIndexVbo
        extends SlimIndex_htm<TagDefIndex, TagDef, TagDefCriteria> {

    public TagDefIndexVbo()
            throws NoSuchPropertyException, ParseException {
        super(TagDefIndex.class);
        indexFields.parse("i*sa", "tagSet", "code", "label", "description", "refCount");
    }

    @Override
    protected TagDefCriteria buildSwitchers(IHttpViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        TagDefCriteria criteria = CriteriaBuilder.fromRequest(new TagDefCriteria(), ctx.getRequest());
        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("向量", false, //
                ctx.query(TagSetDefMapper.class).all(), //
                "tagv", criteria.tagSetId, false);
        criteria.tagSetId = sw.getSelection1();
        return criteria;
    }

    @Override
    protected void dataIndex(IHttpViewContext ctx, DataViewAnchors<TagDef> a, IUiRef<TagDefIndex> ref, IOptions options)
            throws ViewBuilderException, IOException {
        TagDefMapper mapper = ctx.query(TagDefMapper.class);
        TagDefCriteria criteria = ctx.query(TagDefCriteria.class);
        List<TagDef> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (TagDef o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getTagSet().getLabel());
                itab.cocols("cu", tr, o);
                tr.td().text(o.getRefCount());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
