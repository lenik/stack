package com.bee32.zebra.sys.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlTr;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.t.variant.VarMapState;
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.base.schema.TagGroupDef;
import net.bodz.lily.model.base.schema.impl.TagGroupDefMapper;
import net.bodz.lily.model.base.schema.impl.TagGroupDefMask;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

public class TagGroupDefIndex_htm
        extends SlimIndex_htm<TagGroupDefIndex, TagGroupDef, TagGroupDefMask> {

    public TagGroupDefIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(TagGroupDefIndex.class);
        indexFields.parse("i*sa", "schema", "code", "label", "description", "ortho", "tags");
    }

    @Override
    protected TagGroupDefMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        TagGroupDefMask mask = VarMapState.restoreFrom(new TagGroupDefMask(), ctx.getRequest());
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<TagGroupDefIndex> ref)
            throws ViewBuilderException, IOException {
        TagGroupDefMapper mapper = ctx.query(TagGroupDefMapper.class);
        TagGroupDefMask mask = ctx.query(TagGroupDefMask.class);
        List<TagGroupDef> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (TagGroupDef o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getSchema().getLabel());
            itab.cocols("cu", tr, o);
            tr.td().text(o.getTags()).class_("small");
            tr.td().text(o.isOrtho());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
