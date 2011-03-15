package com.bee32.plover.inject;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration({"template-context.xml"})
public class TemplateContext
        extends ConfigResourceObject {

    public TemplateContext() {
        super();
    }

    public TemplateContext(ConfigResourceObject... parents) {
        super(parents);
    }

}
