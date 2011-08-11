package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@Import(AutoContext.class)
@ContextConfiguration("scan-service-context.xml")
public class ScanServiceContext
        extends ConfigSupport {

}
