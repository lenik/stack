package com.bee32.plover.inject.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;

public class DisXmlApplicationContext
        extends AbstractXmlApplicationContext {

    public DisXmlApplicationContext() {
        super();
    }

    public DisXmlApplicationContext(ApplicationContext parent) {
        super(parent);
    }

}
