package com.bee32.plover.scheduler.quartz;

import java.util.ServiceLoader;

import javax.servlet.ServletContextEvent;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.peripheral.DecoratedScl;

public class InitQuartzAndQjcScl
        extends DecoratedScl {

    static Logger logger = LoggerFactory.getLogger(InitQuartzAndQjcScl.class);

    public InitQuartzAndQjcScl() {
        super(QuartzInitializerListener.class);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);

        SchedulerFactory factory = QuartzConfig.getSchedulerFactory(event.getServletContext());

        try {
            Scheduler sched = factory.getScheduler();

            JobFactory jobFactory = new InjectedJobFactory();
            sched.setJobFactory(jobFactory);

        } catch (SchedulerException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        for (IQuartzJobConfigurer configurer : ServiceLoader.load(IQuartzJobConfigurer.class)) {
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
        SchedulerFactory factory = QuartzConfig.getSchedulerFactory(event.getServletContext());

        for (IQuartzJobConfigurer configurer : ServiceLoader.load(IQuartzJobConfigurer.class)) {
            logger.debug("Unload Quartz configuration: " + configurer);
            try {
                configurer.unload(factory);
            } catch (Exception e) {
                logger.error("Failed to unload " + configurer, e);
            }
        }

        super.contextDestroyed(event);
    }

}
