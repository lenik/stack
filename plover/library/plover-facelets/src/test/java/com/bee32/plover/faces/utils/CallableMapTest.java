package com.bee32.plover.faces.utils;

import org.junit.Assert;
import org.junit.Test;

public class CallableMapTest
        extends Assert {

    static class MyFunc
            extends CallableMap {

        @Override
        protected Object invoke(String methodName, Object... args) {

            if ("hello".equals(methodName))
                return "hi, " + args[0];

            throw new UnsupportedOperationException("Method " + methodName + " is undefined");
        }

    }

    static String message = "O.J.'s Kafe";

    @Test
    public void testInvokeHello() {
        MyFunc myf = new MyFunc();
        myf = (MyFunc) myf.get("push");
        myf = (MyFunc) myf.get(message);
        myf = (MyFunc) myf.get("invoke");
        String mesg = (String) myf.get("hello");
        assertEquals("hi, " + message, mesg);
    }

    @Test
    public void testRawHello() {
        MyFunc myf = new MyFunc();
        myf = (MyFunc) myf.get("push");
        myf = (MyFunc) myf.get(message);
        String mesg = (String) myf.get("hello");
        assertEquals("hi, " + message, mesg);
    }

}
