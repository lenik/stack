package com.bee32.plover.inject;

import java.util.ServiceLoader;

import org.springframework.context.ApplicationContext;

public class StaticServiceActivator
        extends InitializingService {

    static boolean staticServiceActivated;

    @Override
    protected void _initialize() {
        activateStaticService(appctx);
    }

    static synchronized void activateStaticService(ApplicationContext appctx) {
        if (staticServiceActivated)
            return;

        for (IActivatorService service : ServiceLoader.load(IActivatorService.class)) {
            logger.info("Activate system service: " + service.getClass().getName());

            service.setApplicationContext(appctx);
            service.activate();
        }
        staticServiceActivated = true;
    }

}
