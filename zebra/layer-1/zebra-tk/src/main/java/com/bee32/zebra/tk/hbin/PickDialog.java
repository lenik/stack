package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;
import net.bodz.bas.html.dom.tag.HtmlDivTag;
import net.bodz.bas.html.dom.tag.HtmlIframeTag;

public class PickDialog
        extends IframeDialog {

    public HtmlDivTag body;
    public HtmlIframeTag iframe;
    public HtmlDivTag buttons;

    public PickDialog(IHtmlTag parent, String id) {
        super(parent, id);
    }

    @Override
    protected void create() {
        addClass("zu-pick-dialog");
    }

}
