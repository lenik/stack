package com.bee32.zebra.sys.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.schema.TagSetDef;
import net.bodz.lily.model.base.schema.impl.TagSetDefCriteria;
import net.bodz.lily.model.base.schema.impl.TagSetDefMapper;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.site.DataViewAnchors;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.CriteriaBuilder;

public class TagSetDefIndex_htm
        extends SlimIndex_htm<TagSetDefIndex, TagSetDef, TagSetDefCriteria> {

    public TagSetDefIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(TagSetDefIndex.class);
        indexFields.parse("i*sa", "schema", "code", "label", "description", "ortho", "tags");
    }

    @Override
    protected TagSetDefCriteria buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        TagSetDefCriteria criteria = CriteriaBuilder.fromRequest(new TagSetDefCriteria(), ctx.getRequest());
        return criteria;
    }

    @Override
    protected void dataIndex(IHtmlViewContext ctx, DataViewAnchors<TagSetDef> a, IUiRef<TagSetDefIndex> ref,
            IOptions options)
            throws ViewBuilderException, IOException {
        TagSetDefMapper mapper = ctx.query(TagSetDefMapper.class);
        TagSetDefCriteria criteria = ctx.query(TagSetDefCriteria.class);
        List<TagSetDef> list = a.noList() ? null : postfilt(mapper.filter(criteria));

        IndexTable itab = new IndexTable(a.data);
        itab.buildHeader(ctx, indexFields.values());
        if (a.dataList())
            for (TagSetDef o : list) {
                HtmlTrTag tr = itab.tbody.tr();
                itab.cocols("i", tr, o);
                tr.td().text(o.getSchema().getLabel());
                itab.cocols("cu", tr, o);
                tr.td().text(o.getTags()).class_("small");
                tr.td().text(o.isOrtho());
                itab.cocols("sa", tr, o);
            }

        if (a.extradata != null)
            dumpFullData(a.extradata, list);
    }

}
