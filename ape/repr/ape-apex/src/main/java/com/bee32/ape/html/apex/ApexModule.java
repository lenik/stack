package com.bee32.ape.html.apex;

import com.bee32.ape.oid.ApeOids;
import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;

@Oid({ 3, 30, ApeOids.Repr, ApeOids.repr.Apex })
public class ApexModule
        extends Module {

    public static final String PREFIX = "/3/30/3/3";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
