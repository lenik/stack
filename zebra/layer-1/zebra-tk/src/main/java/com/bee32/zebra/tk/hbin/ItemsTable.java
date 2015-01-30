package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;

public class ItemsTable
        extends IndexTable {

    boolean colvis = false;

    public ItemsTable(IHtmlTag parent) {
        this(parent, "xtab");
    }

    public ItemsTable(IHtmlTag parent, String id) {
        super(parent, id);

        if (colvis) {
            attr("dom", "<C>rti");
        } else {
            attr("dom", "rti");
            attr("no-colvis", "1");
        }

        attr("no-paginate", "1");

        footColumns = false;

        HtmlDivTag cmds = tools.div().id(id + "cmds");
        cmds.button().class_("cmd-reload").spanText("fa", FA_REFRESH, "刷新");
        cmds.button().class_("cmd-add").spanText("fa", FA_FILE_O, "添加");
    }

}
