package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.dom.IHtmlTag;

public class EditDialog
        extends IframeDialog {

    public EditDialog(IHtmlTag parent, String id) {
        super(parent, id);
    }

    @Override
    protected void create() {
        addClass("zu-edit-dialog");
    }

}
