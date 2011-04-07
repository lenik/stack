package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@Import(AutoContext.class)
@ContextConfiguration({ "scan-stdx-context.xml" })
public class ScanStdxContext
        extends ConfigSupport {

}
