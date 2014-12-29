package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;

public class SectionDiv
        extends HtmlDivTag {

    public HtmlDivTag headDiv;
    public HtmlDivTag iconDiv;
    public HtmlDivTag contentDiv;

    public SectionDiv(IHtmlTag parent, String id, String label, char icon) {
        super(parent, "div");
        this.class_("zu-section");
        headDiv = this.div().class_("zu-section-head");
        iconDiv = headDiv.div().class_("icon fa").text(icon);
        headDiv.div().text(label);
        contentDiv = this.div().class_("zu-section-body");
    }

}
