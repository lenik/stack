package com.bee32.plover.inject;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.test.AbstractFps;

public class ActivatorServiceBooterFps
        extends AbstractFps {

    @Override
    public int getOrder() {
        return -10000;
    }

    @Override
    public void init(ApplicationContext appctx) {
        ActivatorServiceBooter.bootup();
    }

}
