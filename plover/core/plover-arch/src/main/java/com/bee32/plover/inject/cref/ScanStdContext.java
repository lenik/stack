package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("scan-std-context.xml")
public class ScanStdContext
        extends ContextRef {

    ScanStdContext() {
        super();
    }

    ScanStdContext(ContextRef... parents) {
        super(parents);
    }

}
