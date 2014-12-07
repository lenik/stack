package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlTableTag;
import net.bodz.bas.html.dom.tag.HtmlTbodyTag;

public class IndexTable
        extends HtmlTableTag {

    public IHtmlTag[] headFoot;
    public HtmlTbodyTag tbody;

    public IndexTable(IHtmlTag parent, String id) {
        super(parent, "table");

        this.id(id);
        this.class_("table table-striped table-hover table-condensed dataTable");

        headFoot = new IHtmlTag[] { this.thead(), this.tfoot() };
        tbody = this.tbody();
    }

}
