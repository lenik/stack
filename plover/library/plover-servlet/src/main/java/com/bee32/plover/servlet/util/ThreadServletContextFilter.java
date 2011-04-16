package com.bee32.plover.servlet.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.test.Welcome;

public class ThreadServletContextFilter
        implements Filter {

    static Logger logger = LoggerFactory.getLogger(Welcome.class);

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {

        @SuppressWarnings("unused")
        ServletContext servletContext = filterConfig.getServletContext();
        // ThreadServletContext.setServletContext(servletContext);

    }

    @Override
    public void destroy() {
        // ThreadServletContext.setServletContext(null);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.debug("Thread servlet context enter");

        if (request instanceof HttpServletRequest)
            ThreadServletContext.setRequest((HttpServletRequest) request);

        if (response instanceof HttpServletResponse)
            ThreadServletContext.setResponse((HttpServletResponse) response);

        try {
            chain.doFilter(request, response);
        } finally {
            ThreadServletContext.setRequest(null);
            ThreadServletContext.setResponse(null);
        }

    }

}
