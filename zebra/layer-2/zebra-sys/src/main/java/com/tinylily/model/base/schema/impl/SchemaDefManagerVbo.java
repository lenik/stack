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
import com.tinylily.model.base.schema.SchemaDef;

public class SchemaDefManagerVbo
        extends Zc3Template_CEM<SchemaDefManager, SchemaDef> {

    public SchemaDefManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(SchemaDefManager.class);
        formStruct = new SchemaDef().getFormStruct();
        insertIndexFields("i*sa", "code", "label", "description");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<SchemaDefManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        SchemaDefMapper mapper = ctx.query(SchemaDefMapper.class);
        List<SchemaDef> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (SchemaDef o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            cocols("cu", tr, o);
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
