package com.bee32.plover.inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.OrderComparator;

public class ActivatorServiceBooter {

    static Logger logger = LoggerFactory.getLogger(ActivatorServiceBooter.class);

    static boolean booted;

    public static synchronized void bootup() {
        if (booted)
            return;

        List<IActivatorService> services = new ArrayList<IActivatorService>();
        for (IActivatorService service : ServiceLoader.load(IActivatorService.class))
            services.add(service);

        Collections.sort(services, OrderComparator.INSTANCE);

        for (IActivatorService service : services) {
            logger.info("Activate system service: " + service.getClass().getName());
            service.activate();
        }

        booted = true;
    }

}
