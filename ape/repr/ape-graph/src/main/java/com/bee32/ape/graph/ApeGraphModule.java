package com.bee32.ape.graph;

import com.bee32.ape.oid.ApeOids;
import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;

@Oid({ 3, 30, ApeOids.Repr, ApeOids.repr.Graph })
public class ApeGraphModule
        extends Module {

    public static final String PREFIX = "/3/30/3/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
