package com.bee32.sem.frame;

import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;

@Oid({ 3, 15, 1, 1 })
public class SEMFrameModule
        extends Module {

    public static final String PREFIX = "/3/15/1/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
