package com.bee32.plover.servlet;

import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.Core, PloverOids.core.Servlet })
public class PloverServletModule
        extends Module {

    public static final String PREFIX = "/3/12/2/10";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
