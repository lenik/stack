package com.bee32.plover.cache.pull;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.cache.auto.SystemClock;

public class SystemClockTest
        extends Assert {

    @Test
    public void fastForward() {
        SystemClock clock = SystemClock.getInstance();
        long last = clock.tick();

        for (int i = 0; i < 10000; i++) {
            long tick = clock.tick();
            assertTrue(tick >= last);
            last = tick;
        }
    }

    @Test
    public void shortDelay()
            throws InterruptedException {
        SystemClock clock = SystemClock.getInstance();

        for (int repeat = 0; repeat < 10; repeat++)
            for (int delay = 1; delay < 10; delay++) {
                long last = clock.tick();
                Thread.sleep(delay);
                long tick = clock.tick();

                long diff = tick - last;

                // System.out.println(diff);
                assertTrue("Repeat " + repeat + " Delay " + delay + " +  " + diff, //
                        diff >= delay - 2);
            }
    }

    @Test
    public void format() {
        SystemClock clock = SystemClock.getInstance();
        long last = clock.tick();
        assertEquals("1", clock.format(last));
        assertEquals("101", clock.format(last + 100));
    }

}
