package com.bee32.plover.inject;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("legacy-context.xml")
public class LegacyContext
        extends ConfigResourceObject {

    public LegacyContext() {
        super();
    }

    public LegacyContext(ConfigResourceObject... parents) {
        super(parents);
    }

}
