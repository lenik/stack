package com.bee32.plover.web.faces.test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.myfaces.webapp.StartupServletContextListener;

public class MyfacesReinitDummyServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    static StartupServletContextListener myfacesSCL = new StartupServletContextListener();
    static final String FACES_INIT_DONE = "org.apache.myfaces.webapp.StartupServletContextListener.FACES_INIT_DONE";

    @Override
    public void init(ServletConfig config)
            throws ServletException {
        ServletContextEvent sce = new ServletContextEvent(config.getServletContext());

        try {
            myfacesSCL.contextDestroyed(sce);
        } catch (Exception e) {
        }

        // Force to re-initialize.
        ServletContext context = config.getServletContext();
        context.removeAttribute(FACES_INIT_DONE);
        myfacesSCL.contextInitialized(sce);
    }

}
