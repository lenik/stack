package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlIframeTag;

public class PickDialog
        extends HtmlDivTag {

    public HtmlDivTag body;
    public HtmlIframeTag iframe;
    public HtmlDivTag buttons;

    public PickDialog(IHtmlTag parent, String id) {
        this(parent, id, id + "-");
    }

    public PickDialog(IHtmlTag parent, String id, String prefix) {
        super(parent, "div");
        if (id != null)
            id(id);
        class_("dialog zu-pick-dialog");

        iframe = iframe().id(prefix + "iframe");
        // hr();
        // buttons = div().align("right").id(prefix + "buttons");
    }

}
