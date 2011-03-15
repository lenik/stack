package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("auto-context.xml")
public class AutoContext
        extends ContextRef {

    AutoContext() {
        super();
    }

    AutoContext(ContextRef... parents) {
        super(parents);
    }

}
