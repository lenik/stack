package com.bee32.plover.orm.config.main;

import com.bee32.plover.orm.config.CustomizedDataSource;

/**
 * NOTICE: This is instantiated thru bean xml.
 */
public class MainDataSource
        extends CustomizedDataSource {

    public MainDataSource() {
        super("main");

        setInitialSize(10);
    }

    @Override
    protected boolean isP6SpyEnabled() {
        return true;
    }

}
