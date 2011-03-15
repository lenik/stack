package com.bee32.plover.inject.cref;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("template-test-context.xml")
public class TemplateTestContext
        extends ContextRef {

    TemplateTestContext() {
        super();
    }

    TemplateTestContext(ContextRef... parents) {
        super(parents);
    }

}
