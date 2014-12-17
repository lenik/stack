package com.tinylily.model.base.schema.impl;

import java.io.IOException;
import java.util.List;

import net.bodz.bas.c.reflect.NoSuchPropertyException;
import net.bodz.bas.err.ParseException;
import net.bodz.bas.html.IHtmlViewContext;
import net.bodz.bas.html.dom.tag.HtmlTrTag;
import net.bodz.bas.repr.viz.ViewBuilderException;
import net.bodz.bas.rtx.IOptions;
import net.bodz.bas.ui.dom1.IUiRef;

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.TagDef;

public class TagDefManagerVbo
        extends Zc3Template_CEM<TagDefManager, TagDef> {

    public TagDefManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(TagDefManager.class);
        formStruct = new TagDef().getFormStruct();
        insertIndexFields("tagSet", "code", "label", "description", "refCount");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<TagDefManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        TagDefManager manager = ref.get();
        TagDefMapper mapper = manager.getMapper();
        List<TagDef> list = mapper.all();

        titleInfo(p);

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (TagDef o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            tr.td().text(o.getTagSet().getLabel());
            stdcolsCLD(tr, o);
            tr.td().text(o.getRefCount());
            stdcols1(tr, o);
        }

        dumpData(p.extradata, list);

        return ctx;
    }

}
