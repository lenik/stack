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
import net.bodz.bas.ui.dom1.IUiRef;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.base.schema.TagDef;
import net.bodz.lily.model.base.schema.impl.TagDefMapper;
import net.bodz.lily.model.base.schema.impl.TagDefMask;
import net.bodz.lily.model.base.schema.impl.TagGroupDefMapper;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.hbin.SwitcherModel;
import com.bee32.zebra.tk.hbin.SwitcherModelGroup;
import com.bee32.zebra.tk.slim.SlimIndex_htm;
import com.bee32.zebra.tk.util.MaskBuilder;

public class TagDefIndex_htm
        extends SlimIndex_htm<TagDefIndex, TagDef, TagDefMask> {

    public TagDefIndex_htm()
            throws NoSuchPropertyException, ParseException {
        super(TagDefIndex.class);
        indexFields.parse("i*sa", "tagGroup", "code", "label", "description", "refCount");
    }

    @Override
    protected TagDefMask buildSwitchers(IHtmlViewContext ctx, SwitcherModelGroup switchers)
            throws ViewBuilderException {
        TagDefMask mask = MaskBuilder.fromRequest(new TagDefMask(), ctx.getRequest());
        SwitcherModel<Integer> sw;
        sw = switchers.entityOf("向量", false, //
                ctx.query(TagGroupDefMapper.class).all(), //
                "tagv", mask.tagGroupId, false);
        mask.tagGroupId = sw.getSelection1();
        return mask;
    }

    @Override
    protected List<? extends CoObject> dataIndex(IHtmlViewContext ctx, IHtmlOut out, IUiRef<TagDefIndex> ref)
            throws ViewBuilderException, IOException {
        TagDefMapper mapper = ctx.query(TagDefMapper.class);
        TagDefMask mask = ctx.query(TagDefMask.class);
        List<TagDef> list = postfilt(mapper.filter(mask));

        IndexTable itab = new IndexTable(ctx, indexFields.values());
        HtmlTbody tbody = itab.buildViewStart(out);

        for (TagDef o : list) {
            HtmlTr tr = tbody.tr();
            itab.cocols("i", tr, o);
            tr.td().text(o.getTagGroup().getLabel());
            itab.cocols("cu", tr, o);
            tr.td().text(o.getRefCount());
            itab.cocols("sa", tr, o);
        }
        itab.buildViewEnd(tbody);
        return list;
    }

}
