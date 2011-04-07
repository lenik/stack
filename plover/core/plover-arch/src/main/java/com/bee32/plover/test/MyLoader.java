package com.bee32.plover.test;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.support.AbstractContextLoader;

public class MyLoader
        extends AbstractContextLoader {

    public MyLoader() {
        super();
    }

    @Override
    public ApplicationContext loadContext(String... locations)
            throws Exception {
        return null;
    }

    @Override
    protected String getResourceSuffix() {
        return null;
    }

}
