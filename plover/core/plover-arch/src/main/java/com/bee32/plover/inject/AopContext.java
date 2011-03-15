package com.bee32.plover.inject;

import com.bee32.plover.inject.spring.ContextConfiguration;

@ContextConfiguration("aop-context.xml")
public class AopContext
        extends ConfigResourceObject {

    public AopContext() {
        super();
    }

    public AopContext(ConfigResourceObject... parents) {
        super(parents);
    }

}
