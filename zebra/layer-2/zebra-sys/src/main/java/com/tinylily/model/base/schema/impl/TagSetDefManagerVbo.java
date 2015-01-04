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

import com.bee32.zebra.tk.hbin.IndexTable;
import com.bee32.zebra.tk.site.PageStruct;
import com.bee32.zebra.tk.site.Zc3Template_CEM;
import com.tinylily.model.base.schema.TagSetDef;

public class TagSetDefManagerVbo
        extends Zc3Template_CEM<TagSetDefManager, TagSetDef> {

    public TagSetDefManagerVbo()
            throws NoSuchPropertyException, ParseException {
        super(TagSetDefManager.class);
        insertIndexFields("i*sa", "schema", "code", "label", "description", "ortho", "tags");
    }

    @Override
    protected void buildDataView(IHtmlViewContext ctx, PageStruct page, IUiRef<TagSetDefManager> ref, IOptions options)
            throws ViewBuilderException, IOException {
        TagSetDefMapper mapper = ctx.query(TagSetDefMapper.class);
        List<TagSetDef> list = postfilt(mapper.all());

        IndexTable indexTable = mkIndexTable(ctx, page.mainCol, "list");
        for (TagSetDef o : list) {
            HtmlTrTag tr = indexTable.tbody.tr();
            cocols("i", tr, o);
            tr.td().text(o.getSchema().getLabel());
            cocols("cu", tr, o);
            tr.td().text(o.getTags()).class_("small");
            tr.td().text(o.isOrtho());
            cocols("sa", tr, o);
        }

        dumpFullData(page.extradata, list);
    }

}
