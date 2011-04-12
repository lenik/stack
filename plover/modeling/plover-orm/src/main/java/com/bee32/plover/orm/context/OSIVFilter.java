package com.bee32.plover.orm.context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class OSIVFilter
        extends OpenSessionInViewFilter {

    public OSIVFilter() {
        super();
    }

    @Override
    protected SessionFactory lookupSessionFactory() {
        logger.debug("Lookup OSIV session factory");

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        return wac.getBean(SessionFactory.class);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        return super.shouldNotFilter(request);
    }

}