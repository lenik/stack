package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlIframeTag;

public abstract class IframeDialog
        extends HtmlDivTag {

    public HtmlDivTag body;
    public HtmlIframeTag iframe;
    public HtmlDivTag buttons;

    public IframeDialog(IHtmlTag parent, String id) {
        super(parent, "div");
        if (id == null)
            throw new NullPointerException("id");
        id(id);
        class_("dialog");

        String prefix = id + "-";
        iframe = iframe().id(prefix + "fr");
        // hr();
        // buttons = div().align("right").id(prefix + "buttons");

        create();
    }

    protected abstract void create();

}
