package com.bee32.plover.orm.config.test;

import com.bee32.plover.orm.config.CustomizedDataSource;

public class TestDataSource
        extends CustomizedDataSource {

    public TestDataSource() {
        super("test");

        setInitialSize(1);
    }

    @Override
    protected boolean isP6SpyEnabled() {
        return true;
    }

}
