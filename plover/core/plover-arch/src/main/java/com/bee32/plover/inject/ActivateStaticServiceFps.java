package com.bee32.plover.inject;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.test.AbstractFps;

public class ActivateStaticServiceFps
        extends AbstractFps {

    @Override
    public void init(ApplicationContext appctx) {
        StaticServiceActivator.activateStaticService(appctx);
    }

}
