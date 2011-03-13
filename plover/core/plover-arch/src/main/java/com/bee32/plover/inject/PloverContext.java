package com.bee32.plover.inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PloverContext
        extends ClassPathXmlApplicationContext {

    static final String[] ploverContextXmls = { "plover-context.xml", };

    public PloverContext() {
        super(ploverContextXmls);
    }

    public PloverContext(ApplicationContext parent) {
        super(ploverContextXmls, parent);
    }

}
