package com.bee32.sem.world.monetary;

import java.nio.channels.IllegalSelectorException;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.bee32.plover.servlet.util.ThreadHttpContext;

public class FxrProviderFactory {

    static ApplicationContext getApplicationContext() {
        ApplicationContext context = ThreadHttpContext.getApplicationContext();

        if (context == null) {
            // WiredTestCase...?
            throw new IllegalSelectorException();
        }

        return context;
    }

    public static IFxrProvider getFxrProvider() {
        ApplicationContext appctx = getApplicationContext();

        Map<String, IFxrProvider> beans = appctx.getBeansOfType(IFxrProvider.class);
        if (beans.isEmpty())
            return null;

        IFxrProvider first = beans.values().iterator().next();
        return first;
    }

}
