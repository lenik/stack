package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("stdscan-context.xml")
public class StdScanContext
        extends ContextRef {

    StdScanContext() {
        super();
    }

    StdScanContext(ContextRef... parents) {
        super(parents);
    }

}
