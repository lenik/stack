package com.bee32.plover.scheduler.quartz;

import java.util.ServiceLoader;

import javax.servlet.ServletContextEvent;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class QuartzInitializerListener
        extends org.quartz.ee.servlet.QuartzInitializerListener {

    static Logger logger = LoggerFactory.getLogger(QuartzInitializerListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);

        SchedulerFactory factory = QuartzConfig.getSchedulerFactory(event.getServletContext());

        try {
            Scheduler sched = factory.getScheduler();
            sched.setJobFactory(new SpringBeanJobFactory());
        } catch (SchedulerException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

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
    public void contextDestroyed(ServletContextEvent event) {
        super.contextDestroyed(event);

        SchedulerFactory factory = QuartzConfig.getSchedulerFactory(event.getServletContext());

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
