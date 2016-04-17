package com.bee32.zebra.tk.hbin;

import net.bodz.bas.c.string.StringArray;
import net.bodz.bas.html.io.IHtmlOut;

public class SimpleDialog {

    public IHtmlOut build(IHtmlOut out, String id, String... styleClasses) {
        if (id == null)
            throw new NullPointerException("id");
        out = out.div();
        out.id(id);
        out.class_("dialog " + StringArray.join(" ", styleClasses));
        return out;
    }

}
