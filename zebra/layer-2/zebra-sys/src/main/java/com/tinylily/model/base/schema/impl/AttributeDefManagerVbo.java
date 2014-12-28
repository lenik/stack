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
import com.tinylily.model.base.schema.AttributeDef;

public class AttributeDefManagerVbo
        extends Zc3Template_CEM<AttributeDefManager, AttributeDef> {

    public AttributeDefManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(AttributeDefManager.class);
        formStruct = new AttributeDef().getFormStruct();
        insertIndexFields("schema", "code", "label", "description", "refCount");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<AttributeDefManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        AttributeDefManager manager = ref.get();
        AttributeDefMapper mapper = manager.getMapper();
        List<AttributeDef> list = filter1(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (AttributeDef o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            stdcols0(tr, o);
            tr.td().text(o.getSchema().getLabel());
            stdcolsCLD(tr, o);
            tr.td().text(o.getRefCount());
            stdcols1(tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
