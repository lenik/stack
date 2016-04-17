package com.bee32.zebra.tk.hbin;

import net.bodz.bas.html.io.IHtmlOut;

public class PickDialog
        extends IframeDialog_htm1 {

    public static void build(IHtmlOut out, String id) {
        new IframeDialog_htm1().build(out, id, "zu-pick-dialog");
    }

}
