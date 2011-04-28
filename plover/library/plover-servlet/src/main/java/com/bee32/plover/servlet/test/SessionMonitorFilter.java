package com.bee32.plover.servlet.test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionMonitorFilter
        implements Filter {

    public static final String INIT_KEY = SessionMonitorFilter.class.getName();

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if ((request instanceof HttpServletRequest)) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();

            Long counter = (Long) session.getAttribute(INIT_KEY);

            if (counter == null) {
                SessionMonitors.INSTANCE.initSession(session);
                counter = 0L;
            }

            counter++;
            session.setAttribute(INIT_KEY, counter);
        }
        chain.doFilter(request, response);
    }

}
