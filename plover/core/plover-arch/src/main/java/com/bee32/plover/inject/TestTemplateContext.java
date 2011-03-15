package com.bee32.plover.inject;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("testtemplate-context.xml")
public class TestTemplateContext
        extends ConfigResourceObject {

    public TestTemplateContext() {
        super();
    }

    public TestTemplateContext(ConfigResourceObject... parents) {
        super(parents);
    }

}
