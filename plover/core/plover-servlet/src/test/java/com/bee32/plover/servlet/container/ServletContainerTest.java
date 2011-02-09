package com.bee32.plover.servlet.container;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bee32.plover.inject.ContextException;
import com.bee32.plover.inject.IAware;
import com.bee32.plover.inject.IContainer;
import com.bee32.plover.test.servlet.ServletTestCase;

public class ServletContainerTest
        extends ServletTestCase {

    private ServletContainer application;

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
        miniServer.addListener(application);
        miniServer.start();
    }

    @After
    public void teardown() {
        miniServer.end();
    }

    class SayHello
            implements IAware {

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
        SayHello c1 = new SayHello();

        String actual = c1.hello();
        // assertEquals("hello, lucy", actual);
    }

    @Test
    public void b() {

    }

}
