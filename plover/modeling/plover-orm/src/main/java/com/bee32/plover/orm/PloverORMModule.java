package com.bee32.plover.orm;

import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.Library, PloverOids.library.ORM })
public class PloverORMModule
        extends ERModule {

    public static final String PREFIX = "/3/12/3/4";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
