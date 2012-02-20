package com.bee32.plover.faces;

import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.Webui, PloverOids.webui.Facelets })
public class PloverFaceletsModule
        extends Module {

    public static final String PREFIX = "/3/12/8/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
