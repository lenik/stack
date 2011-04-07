package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@Import(AutoContext.class)
@ContextConfiguration("scan-testx-context.xml")
public class ScanTestxContext
        extends ContextRef {

    ScanTestxContext() {
        super();
    }

    ScanTestxContext(ContextRef... parents) {
        super(parents);
    }

}
