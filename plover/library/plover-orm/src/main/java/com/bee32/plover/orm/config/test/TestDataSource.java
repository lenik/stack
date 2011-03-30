package com.bee32.plover.orm.config.test;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.qualifier.TestPurpose;
import com.bee32.plover.orm.config.CustomizedDataSource;

@Component
@TestPurpose
@Lazy
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
