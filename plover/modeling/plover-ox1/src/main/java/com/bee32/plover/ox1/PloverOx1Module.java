package com.bee32.plover.ox1;

import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.Library, PloverOids.library.ORMExt })
public class PloverOx1Module
        extends Module {

    public static final String PREFIX = "/3/12/3/5";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        // still, no pages for -ox1 yet.
    }

}
