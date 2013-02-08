package com.bee32.ape.engine;

import com.bee32.ape.oid.ApeOids;
import com.bee32.plover.arch.Module;
import com.bee32.plover.pub.oid.Oid;

@Oid({ 3, 30, ApeOids.Execution, ApeOids.execution.Engine })
public class ApeEngineModule
        extends Module {

    @Override
    protected void preamble() {
    }

}
