package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration({ "scan-stdx-context.xml" })
public class ScanStdxContext
        extends ContextRef {

    ScanStdxContext() {
        super();
    }

    ScanStdxContext(ContextRef... parents) {
        super(parents);
    }

}
