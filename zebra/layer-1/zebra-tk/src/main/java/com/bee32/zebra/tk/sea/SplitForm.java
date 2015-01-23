package com.bee32.zebra.tk.sea;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlFormTag;

public class SplitForm
        extends HtmlFormTag {

    public final HtmlDivTag head;

    public SplitForm(IHtmlTag parent) {
        super(parent, "form");
        head = div().class_("form-head");
    }

}