package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;

public abstract class SimpleDialog
        extends HtmlDivTag {

    public HtmlDivTag body;
    public HtmlDivTag buttons;

    public SimpleDialog(IHtmlTag parent, String id) {
        super(parent, "div");
        if (id == null)
            throw new NullPointerException("id");
        id(id);
        class_("dialog");
    }

    protected abstract void build();

}
