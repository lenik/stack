package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlDiv;

public class SectionDiv_htm1 {

    public String label;
    public char icon;

    public SectionDiv_htm1(String label, char icon) {
        this.label = label;
        this.icon = icon;
    }

    public IHtmlOut build(IHtmlOut out, String id) {
        out = out.div();

        out.id(id);
        out.class_("zu-section");

        HtmlDiv headDiv = out.div().class_("zu-section-head");
        {

            HtmlDiv iconDiv = headDiv.div().class_("icon fa");
            iconDiv.text(icon);

            if (label != null)
                headDiv.div().text(label);
        }

        HtmlDiv contentDiv = out.div().class_("zu-section-body");
        buildContent(contentDiv);
        return contentDiv;
    }

    protected void buildContent(HtmlDiv out) {
    }

}
