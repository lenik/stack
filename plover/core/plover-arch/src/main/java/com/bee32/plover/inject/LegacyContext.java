package com.bee32.plover.inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LegacyContext
        extends ClassPathXmlApplicationContext {

    static final String[] legacyContextXmls = { "legacy-context.xml", };

    public LegacyContext() {
        super(legacyContextXmls, LegacyContext.class);
    }

    public LegacyContext(ApplicationContext parent) {
        super(legacyContextXmls, LegacyContext.class, parent);
    }

}
