package com.bee32.plover.restful;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.Dispatcher;

public class DispatcherFilter
        implements Filter {

    private static final long serialVersionUID = 1L;

    static Dispatcher dispatcher;
    static ModuleManager moduleManager;
    static {
        dispatcher = new Dispatcher();
        moduleManager = new ModuleManager();
    }

    private ServletContext servletContext;
    protected String contextPath; // Not used.

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        servletContext = filterConfig.getServletContext();
        contextPath = servletContext.getContextPath();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            String pathInfo = req.getPathInfo();

            Object target;
            try {
                target = dispatcher.dispatch(moduleManager, pathInfo);
            } catch (DispatchException e) {
                // resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
                throw new ServletException(e.getMessage(), e);
            }

            if (target != null) {
                PrintWriter out = response.getWriter();
                out.println(target);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        servletContext = null;
    }

}
