package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("stdscan-test-context.xml")
public class StdScanTestContext
        extends ContextRef {

    StdScanTestContext() {
        super();
    }

    StdScanTestContext(ContextRef... parents) {
        super(parents);
    }

}
