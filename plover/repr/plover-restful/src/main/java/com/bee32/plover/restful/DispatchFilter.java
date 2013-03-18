package com.bee32.plover.restful;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.test.context.ContextConfiguration;

import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.servlet.context.ServletContextUtil;

/**
 * The overall modules dispatcher.
 * <p>
 * The dispatch filter can be used in both servlet-mapping and filter-mapping form.
 * <p>
 * In the servlet-mapping form, only path starts with-in a specific namespace will be served by the
 * dispatch filter.
 * <p>
 * In the filter-mapping form, the dispatched namespace is mess up with the ordinary servlets and
 * jsp urls.
 */
@ContextConfiguration
public class DispatchFilter
        extends HttpServlet
        implements Filter, InitializingBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(DispatchFilter.class);

    private ServletContext servletContext;
    protected String contextPath; // Not used.

    @Inject
    RESTfulService restfulService;

    public DispatchFilter() {
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
    }

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        servletContext = filterConfig.getServletContext();
        contextPath = servletContext.getContextPath();

        ServletContextUtil.wire(servletContext, this);
    }

    @Override
    public void destroy() {
        servletContext = null;
    }

    @Override
    public void doFilter(ServletRequest _request, ServletResponse _response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) _request;
        HttpServletResponse response = (HttpServletResponse) _response;

        if (prefilter(request)) {
            try {
                if (restfulService.processOrNot(request, response))
                    return;
            } catch (RESTfulException e) {
                throw new ServletException(e.getMessage(), e);
            }
        }

        chain.doFilter(_request, _response);
    }

    protected boolean prefilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        int slash = path.indexOf('/');
        String first = slash == -1 ? path : path.substring(0, slash);

        return OidUtil.isNumber(first);
    }

}
