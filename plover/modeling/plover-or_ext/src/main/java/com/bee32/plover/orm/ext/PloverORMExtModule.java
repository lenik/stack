package com.bee32.plover.orm.ext;

import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.Library, PloverOids.library.ORMExt })
public class PloverORMExtModule
        extends Module {

    public static final String PREFIX = "/3/12/3/5";

    @Override
    protected void preamble() {
    }

}
