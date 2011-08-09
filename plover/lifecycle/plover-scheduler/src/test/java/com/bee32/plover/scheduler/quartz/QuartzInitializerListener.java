package com.bee32.plover.scheduler.quartz;

import java.util.ServiceLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.quartz.SchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzInitializerListener
        extends org.quartz.ee.servlet.QuartzInitializerListener {

    static Logger logger = LoggerFactory.getLogger(QuartzInitializerListener.class);

    static SchedulerFactory getSchedulerFactory(ServletContext servletContext) {
        String factoryKey = servletContext.getInitParameter("quartz:servlet-context-factory-key");
        if (factoryKey == null)
            factoryKey = servletContext.getInitParameter("servlet-context-factory-key");
        if (factoryKey == null)
            factoryKey = QUARTZ_FACTORY_KEY;

        Object factory = servletContext.getAttribute(factoryKey);
        return (SchedulerFactory) factory;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);

        SchedulerFactory factory = getSchedulerFactory(sce.getServletContext());

        for (IQuartzConfigurer configurer : ServiceLoader.load(IQuartzConfigurer.class)) {
            logger.debug("Load Quartz configuration: " + configurer);
            try {
                configurer.load(factory);
            } catch (Exception e) {
                logger.error("Failed to load " + configurer, e);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);

        SchedulerFactory factory = getSchedulerFactory(sce.getServletContext());

        for (IQuartzConfigurer configurer : ServiceLoader.load(IQuartzConfigurer.class)) {
            logger.debug("Unload Quartz configuration: " + configurer);
            try {
                configurer.unload(factory);
            } catch (Exception e) {
                logger.error("Failed to unload " + configurer, e);
            }
        }
    }

}
