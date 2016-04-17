package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlTable;
import net.bodz.bas.html.io.tag.HtmlTbody;
import net.bodz.bas.html.io.tag.HtmlThead;
import net.bodz.bas.html.util.IFontAwesomeCharAliases;

public class DataTable
        implements IFontAwesomeCharAliases {

    boolean colvis = false;
    String[] columns;

    public DataTable(String... columns) {
        this.columns = columns;
    }

    public void build(IHtmlOut out) {
        build(out, null);
    }

    public HtmlTbody build(IHtmlOut out, String id) {
        HtmlTable table = out.table();

        if (id != null)
            table.id(id);

        // table.style("width: 100%");
        table.class_("table table-striped table-hover table-condensed dataTable table-responsive");

        if (colvis) {
            table.attr("dom", "<C>rti");
        } else {
            table.attr("dom", "rti");
            table.attr("no-colvis", "1");
        }

        table.attr("no-paginate", "1");

        HtmlThead head = table.thead();
        for (String column : columns)
            head.th().text(column);

        return table.tbody();
    }

}
