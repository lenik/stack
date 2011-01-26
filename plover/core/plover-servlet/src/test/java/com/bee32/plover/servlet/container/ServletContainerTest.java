package com.bee32.plover.servlet.container;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bee32.plover.inject.ContextException;
import com.bee32.plover.inject.IComponent;
import com.bee32.plover.inject.IContainer;
import com.bee32.plover.servlet.unit.ServletUnit;

public class ServletContainerTest
        extends ServletUnit {

    ServletContainer container = new ServletContainer();

    class MiniServer {
        ServletContextListener listener;

        void addListener(ServletContextListener listener) {
            this.listener = listener;
        }

        void start() {
            ServletContextEvent event = new ServletContextEvent(application);
            listener.contextInitialized(event);
        }

        void end() {
            ServletContextEvent event = new ServletContextEvent(application);
            listener.contextDestroyed(event);
        }
    }

    MiniServer miniServer;

    @Before
    public void setup() {
        miniServer = new MiniServer();
        miniServer.addListener(container);
        miniServer.start();
    }

    @After
    public void teardown() {
        miniServer.end();
    }

    class Component1
            implements IComponent {

        ServletContext app;
        HttpServletRequest request;

        @Override
        public void enter(IContainer container)
                throws ContextException {
            app = container.require(ServletContext.class);
            request = container.require(HttpServletRequest.class);
        }

        @Override
        public void leave(IContainer container)
                throws ContextException {
            app = null;
            request = null;
        }

        public String hello() {
            String name = request.getParameter("name");
            return "hello, " + name;
        }

    }

    @Test
    public void test1() {
        Component1 c1 = new Component1();

        String actual = c1.hello();
        // assertEquals("hello, lucy", actual);
    }

    @Test
    public void b() {

    }

}
