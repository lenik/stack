package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.viz.IHtmlViewContext;
import net.bodz.bas.repr.form.PathField;

public class ItemsTable
        extends IndexTable {

    public boolean colvis = false;
    public String editorUrl;

    public ItemsTable(IHtmlViewContext ctx, Iterable<PathField> indexFields) {
        super(ctx, indexFields);
        footColumns = false;
    }

    @Override
    public HtmlTbody buildViewStart(IHtmlOut out, String id) {
        attrs.put("data-editurl", editorUrl);

        if (colvis) {
            attrs.put("dom", "<C>rti");
        } else {
            attrs.put("dom", "rti");
            attrs.put("no-colvis", "1");
        }

        attrs.put("no-paginate", "1");
        return super.buildViewStart(out, id);
    }

    @Override
    protected void buildTools(HtmlDiv tools, String tabId) {
        HtmlDiv cmds = tools.div().id(tabId + "cmds");
        cmds.button().class_("btn-reload").iText(FA_REFRESH, "fa").text("刷新");
        cmds.button().class_("btn-add").iText(FA_FILE_O, "fa").text("添加");
    }

}
