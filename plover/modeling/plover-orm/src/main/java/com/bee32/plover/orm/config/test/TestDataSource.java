package com.bee32.plover.orm.config.test;

import com.bee32.plover.orm.config.CustomizedDataSource;

/**
 * NOTICE: This is instantiated thru bean xml.
 */
public class TestDataSource
        extends CustomizedDataSource {

    public static String NAME = "test";

    public TestDataSource() {
        super(NAME);

        setInitialSize(1);
    }

    @Override
    protected boolean isP6SpyEnabled() {
        return true;
    }

}
