package com.bee32.plover.orm;

import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.Library, PloverOids.library.ORM })
public class PloverOrmModule
        extends Module {

    @Override
    protected void preamble() {
    }

}
