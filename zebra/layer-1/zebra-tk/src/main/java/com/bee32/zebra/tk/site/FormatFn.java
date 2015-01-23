package com.bee32.zebra.tk.site;

import net.bodz.bas.c.java.util.Dates;
import net.bodz.bas.html.dom.AbstractHtmlTag;

import com.tinylily.model.base.CoObject;

public class FormatFn {

    public <tag_t extends AbstractHtmlTag<?>> tag_t ref(tag_t tag, CoObject e) {
        if (e != null)
            tag.text(e.getLabel());
        return tag;
    }

    public String formatDate(Object date) {
        if (date == null)
            return null;
        else
            return Dates.YYYY_MM_DD.format(date);
    }

}
