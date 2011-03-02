package com.bee32.plover.disp.type;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.DispatchUtil;
import com.bee32.plover.disp.util.TokenQueue;

public class MethodDispatcherTest
        extends Assert {

    public String method1(String a, int b) {
        return a + ":" + b;
    }

    public String say(String name) {
        return "hey, " + name;
    }

    @Test
    public void test_Si()
            throws DispatchException {
        MethodDispatcher disp = new MethodDispatcher();
        TokenQueue tq = new TokenQueue("method1:Si/hello/3");
        Object actual = DispatchUtil.dispatch(disp, this, tq);
        assertEquals("hello:3", actual);
    }

    @Test
    public void test_say()
            throws DispatchException {
        MethodDispatcher disp = new MethodDispatcher();
        TokenQueue tq = new TokenQueue("say/clock");
        Object actual = DispatchUtil.dispatch(disp, this, tq);
        assertEquals("hey, clock", actual);
    }

}
