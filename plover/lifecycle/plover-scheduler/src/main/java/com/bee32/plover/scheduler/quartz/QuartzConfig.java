package com.bee32.plover.scheduler.quartz;

import javax.free.IllegalUsageException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bee32.plover.servlet.test.ServletTestLibrary;

public class QuartzConfig {

    public static final String SC_FACTORY_KEY_NAME = "servlet-context-factory-key";
    public static final String SC_FACTORY_KEY = ".schedulerFactory";

    /**
     * This is a fixed constant defined in Quartz system.
     *
     * @see QuartzInitializerListener
     */
    public static final String SCHED_SC_KEY_NAME = "scheduler-context-servlet-context-key";

    /**
     * This is the real sched/sc key.
     *
     * @see QuartzWac#configureServlets(ServletTestLibrary)
     */
    public static final String SCHED_SC_KEY = ".servletContext";

    public static SchedulerFactory getSchedulerFactory(ServletContext servletContext) {
        String factoryKey = servletContext.getInitParameter("quartz:servlet-context-factory-key");
        if (factoryKey == null)
            factoryKey = servletContext.getInitParameter(SC_FACTORY_KEY_NAME);
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

    public static ApplicationContext getApplicationContext(SchedulerContext schedulerContext) {
        ApplicationContext ac = getApplicationContextOpt(schedulerContext);
        if (ac == null)
            throw new IllegalStateException("No application context setup for scheduler context " + schedulerContext);
        return ac;
    }

    public static ApplicationContext getApplicationContextOpt(SchedulerContext schedulerContext) {
        Object _servletContext = schedulerContext.get(SCHED_SC_KEY);
        if (_servletContext == null)
            throw new IllegalUsageException("Scheduler/ServletContext attribute isn't set.");

        ServletContext servletContext = (ServletContext) _servletContext;

        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        return wac;
    }

}
