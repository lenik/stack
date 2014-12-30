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
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.SwitchOverride;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.AttributeDef;

public class AttributeDefManagerVbo
        extends Zc3Template_CEM<AttributeDefManager, AttributeDef> {

    public AttributeDefManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(AttributeDefManager.class);
        formDef = new AttributeDef().getFormDef();
        insertIndexFields("i*sa", "schema", "code", "label", "description", "refCount");
    }

    @Override
    public IHtmlViewContext buildHtmlView(IHtmlViewContext ctx, IUiRef<AttributeDefManager> ref, IOptions options)
            throws ViewBuilderException, IOException {

        ctx = super.buildHtmlView(ctx, ref, options);
        PageStruct p = new PageStruct(ctx);

        AttributeDefMapper mapper = ctx.query(AttributeDefMapper.class);

        AttributeDefCriteria criteria = criteriaFromRequest(new AttributeDefCriteria(), ctx.getRequest());
        FilterSectionDiv filters = new FilterSectionDiv(p.mainCol, "s-filter");
        {
            SwitchOverride<Integer> so;
            so = filters.switchEntity("模式", false, //
                    ctx.query(SchemaDefMapper.class).all(), //
                    "schema", criteria.schemaId, false);
            criteria.schemaId = so.key;
        }

        List<AttributeDef> list = postfilt(mapper.filter(criteria));

        IndexTable indexTable = mkIndexTable(p.mainCol, "list");
        for (AttributeDef o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(o.getSchema().getLabel());
            cocols("cu", tr, o);
            tr.td().text(o.getRefCount());
            cocols("sa", tr, o);
        }

        dumpFullData(p.extradata, list);

        return ctx;
    }

}
