package com.bee32.plover.orm.config.main;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.config.CustomizedDataSource;

@Component
@Lazy
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
