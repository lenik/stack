package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlTbodyTag;
import net.bodz.bas.html.dom.tag.HtmlTheadTag;
import net.bodz.bas.html.dom.tag._HtmlTableTag;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

public class DataTable
        extends _HtmlTableTag<DataTable>
        implements IFontAwesomeCharAliases {

    boolean colvis = false;

    public HtmlTheadTag head;
    public HtmlTbodyTag body;

    public DataTable(IHtmlTag parent) {
        this(parent, null);
    }

    public DataTable(IHtmlTag parent, String id) {
        super(parent, "table");
        if (id != null)
            id(id);

        // table.style("width: 100%");
        class_("table table-striped table-hover table-condensed dataTable table-responsive");

        if (colvis) {
            attr("dom", "<C>rti");
        } else {
            attr("dom", "rti");
            attr("no-colvis", "1");
        }

        attr("no-paginate", "1");

        head = thead();
        body = tbody();
    }

}
