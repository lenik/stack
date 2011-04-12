package com.bee32.plover.restful;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.servlet.context.ServletContextUtil;

public class DispatchServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(DispatchFilter.class);

    @Inject
    RESTfulService restfulService;

    @Override
    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);

        ServletContext servletContext = config.getServletContext();
        ServletContextUtil.wire(servletContext, this);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            if (!restfulService.processOrNot(req, resp)) {
                // Dispatched to nothing.
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (RESTfulException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

}
