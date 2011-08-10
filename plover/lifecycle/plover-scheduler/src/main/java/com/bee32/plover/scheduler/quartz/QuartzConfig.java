package com.bee32.plover.scheduler.quartz;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

public class QuartzConfig {

    public static SchedulerFactory getSchedulerFactory(ServletContext servletContext) {
        String factoryKey = servletContext.getInitParameter("quartz:servlet-context-factory-key");
        if (factoryKey == null)
            factoryKey = servletContext.getInitParameter("servlet-context-factory-key");
        if (factoryKey == null) {
            // QIServlet: "org.quartz.impl.StdSchedulerFactory.KEY";
            // QIListener: "org.quartz.impl.StdSchedulerFactory.KEY"
            factoryKey = QuartzInitializerListener.QUARTZ_FACTORY_KEY;
        }

        Object factory = servletContext.getAttribute(factoryKey);
        return (SchedulerFactory) factory;
    }

    public static Scheduler getScheduler(ServletContext servletContext)
            throws SchedulerException {
        SchedulerFactory factory = getSchedulerFactory(servletContext);
        Scheduler scheduler = factory.getScheduler();
        return scheduler;
    }

    public static Scheduler getScheduler(HttpServletRequest request)
            throws SchedulerException {
        ServletContext servletContext = request.getSession().getServletContext();
        Scheduler scheduler = getScheduler(servletContext);
        return scheduler;
    }

}
