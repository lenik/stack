package com.bee32.plover.disp.util;

import org.junit.Assert;
import org.junit.Test;

public class ArrivalTest
        extends Assert {

    @Test
    public void testBacktrace() {
        Arrival a = new Arrival("a");
        Arrival b = new Arrival(a, "b", "foo", "bar");
        Arrival c = new Arrival(b, "c", "cat");
        Arrival d = new Arrival(c, null, "dog");
        Arrival e = new Arrival(d, null, "xyz");

        class FindC
                implements ArrivalBacktraceCallback<RuntimeException> {

            @Override
            public boolean arriveBack(IArrival arrival) {
                Object target = arrival.getTarget();
                return "c".equals(target);
            }

        }
        FindC findC = new FindC();

        assertTrue(e.backtrace(findC));
        assertTrue(d.backtrace(findC));
        assertTrue(c.backtrace(findC));
        assertFalse(b.backtrace(findC));
    }

}
