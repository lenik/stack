package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@Import(AutoContext.class)
@ContextConfiguration("scan-test-context.xml")
public class ScanTestContext
        extends ContextRef {

    ScanTestContext() {
        super();
    }

    ScanTestContext(ContextRef... parents) {
        super(parents);
    }

}
