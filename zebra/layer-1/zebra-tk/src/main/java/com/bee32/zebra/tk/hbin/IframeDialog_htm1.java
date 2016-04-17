package com.bee32.zebra.tk.hbin;

import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.html.io.IHtmlOut;
import net.bodz.bas.html.io.tag.HtmlIframe;

public class IframeDialog_htm1 {

    String parentItabId;

    public void build(IHtmlOut out, String id, String... styleClasses) {
        if (id == null)
            throw new NullPointerException("id");

        out = out.div();
        out.id(id);
        out.class_("dialog " + StringArray.join(" ", styleClasses));

        String prefix = id + "-";
        HtmlIframe iframe = out.iframe();
        iframe.id(prefix + "fr");
        if (parentItabId != null)
            iframe.attr("parentItabId", parentItabId);

        // out.hr();
        // out.div().align("right").id(prefix + "buttons");
    }

}
