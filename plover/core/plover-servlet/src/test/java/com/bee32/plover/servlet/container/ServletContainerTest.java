package com.bee32.plover.servlet.container;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import com.bee32.plover.inject.ContextException;
import com.bee32.plover.inject.IAware;
import com.bee32.plover.inject.IContainer;
import com.bee32.plover.servlet.test.ServletTestCase;

public class ServletContainerTest
        extends ServletTestCase {

    private ServletContainer application;

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

    // @Test
    public void testRegular() {
        SayHello c1 = new SayHello();
        String actual = c1.hello();
        // assertEquals("hello, lucy", actual);
    }

    @Test
    public void b() {

    }

}
