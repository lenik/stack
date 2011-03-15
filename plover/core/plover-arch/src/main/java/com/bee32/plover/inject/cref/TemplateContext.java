package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration({ "template-context.xml" })
public class TemplateContext
        extends ContextRef {

    TemplateContext() {
        super();
    }

    TemplateContext(ContextRef... parents) {
        super(parents);
    }

}
