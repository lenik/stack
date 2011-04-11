package com.bee32.plover.orm.context;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSIVFilter
        implements Filter {

    static Logger logger = LoggerFactory.getLogger(OSIVFilter.class);

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        logger.debug("Initializing filter...");
        logger.debug("Obtaining SessionFactory from static HibernateUtil singleton");
        sessionFactory = null;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            logger.debug("Starting a database transaction");
            sessionFactory.getCurrentSession().beginTransaction();

            // Call the next filter (continue request processing)
            chain.doFilter(request, response);

            // Commit and cleanup
            logger.debug("Committing the database transaction");
            sessionFactory.getCurrentSession().getTransaction().commit();

        } catch (StaleObjectStateException staleEx) {
            logger.error("This interceptor does not implement optimistic concurrency control!");
            logger.error("Your application will not work until you add compensation actions!");
            // Rollback, close everything, possibly compensate for any permanent changes
            // during the conversation, and finally restart business conversation. Maybe
            // give the user of the application a chance to merge some of his work with
            // fresh data... what you do here depends on your applications design.
            throw staleEx;
        } catch (Throwable ex) {
            // Rollback only
            ex.printStackTrace();
            try {
                if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                    logger.debug("Trying to rollback database transaction after exception");
                    sessionFactory.getCurrentSession().getTransaction().rollback();
                }
            } catch (Throwable rbEx) {
                logger.error("Could not rollback transaction after exception!", rbEx);
            }

            // Let others handle it... maybe another interceptor for exceptions?
            throw new ServletException(ex);
        }
    }

}